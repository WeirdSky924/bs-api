package cn.weirdsky.department.service.impl;


import cn.weirdsky.entity.entity.Department;
import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.qo.DepartmentQo;
import cn.weirdsky.entity.entity.vo.DepartmentVo;
import cn.weirdsky.mapper.mapper.DepartmentMapper;
import cn.weirdsky.department.service.DepartmentService;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtill;

    @Override
    public List<Department> getAll() {
        return departmentMapper.getAll();
    }

    @Override
    public Boolean saveDepartment(Department department) {
        LoginUser user = loginUtil.getLoginUser();
        department.setCreateId(user.getUserId());
        department.setCreateName(user.getUserName());
        department.setCreateTime(new Date());
        department.setDeleteMark("0");
        department.setDepartmentId(tableUtill.getTableId("department"));
        int insert = departmentMapper.insert(department);
        return insert == 1;
    }

    @Override
    public Integer updateDepartment(Department department) {
        String departmentAdminUserIds = department.getDepartmentAdminUserIds();
        LoginUser loginUser = loginUtil.getLoginUser();
        if (loginUser.getUserType().equals("0") || departmentAdminUserIds.indexOf(loginUser.getDeptId())!=-1){
            QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("deleteMark", "0");
            queryWrapper.eq(department.getDepartmentId() != null,"departmentId", department.getDepartmentId());
            return departmentMapper.update(department, queryWrapper);
        }
        return 0;
    }

    @Override
    public Integer deleteDepartment(String departmentIds) {
        String[] ids = departmentIds.split(",");
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("departmentId", ids);
        Department department = new Department();
        department.setDeleteMark("1");
        return departmentMapper.update(department, queryWrapper);
    }

    @Override
    public List<Department> getBySearch(DepartmentQo departmentQo) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("l.deleteMark", "0");
        if (stringUtil.IsNotEmpty(departmentQo.getStartTime()) && stringUtil.IsNotEmpty(departmentQo.getEndTime())){
            queryWrapper.between("l.visitorTime", departmentQo.getStartTime(), departmentQo.getEndTime());
        }
        queryWrapper.eq(stringUtil.IsNotEmpty(departmentQo.getDepartmentId()), "departmentId", departmentQo.getDepartmentId());
        queryWrapper.like(stringUtil.IsNotEmpty(departmentQo.getSearch()), "departmentName", departmentQo.getSearch());
        return departmentMapper.getBySearch(queryWrapper);
    }

    @Override
    public List<Department> getById(String departmentIds) {
        List<Department> departments = new ArrayList<>();
        String[] ids = departmentIds.split(",");
        for (String id: ids) {
            if (stringUtil.IsNotEmpty(id)){
                departments.add(departmentMapper.selectById(id));
            }
        }
        return departments;
    }

    @Override
    public Department getLoginDepartment() {
        LoginUser loginUser = loginUtil.getLoginUser();
        Department department = departmentMapper.selectById(loginUser.getDeptId());
        return department;
    }

    @Override
    public Boolean saveOrUpdateDepartment(DepartmentQo departmentQo) {
        if (stringUtil.IsEmpty(departmentQo.getDepartmentId())) {
            return saveDepartment(departmentQo);
        }
        if (departmentMapper.selectById(departmentQo.getDepartmentId()) == null) {
            return false;
        }
        return updateDepartment(departmentQo) == 1;
    }

    @Override
    public Boolean getMyDepartment() {
        Department loginDepartment = getLoginDepartment();
        LoginUser loginUser = loginUtil.getLoginUser();
        String userId = loginUser.getUserId();
        return loginDepartment.getDepartmentAdminUserIds().contains(userId);
    }

    @Override
    public List<DepartmentVo> getVisitorLogDepartmentVo() {
        return departmentMapper.getVisitorLogDepartmentVo();
    }
}

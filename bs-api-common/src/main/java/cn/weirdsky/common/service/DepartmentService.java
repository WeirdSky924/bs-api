package cn.weirdsky.common.service;

import cn.weirdsky.common.entity.Department;
import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.entity.qo.DepartmentQo;

import java.util.List;

public interface DepartmentService {
    /**
     * 统计所有部门信息
     * @return
     */
    List<Department> getAll();

    /**
     * 保存部门信息
     * @param department
     * @return
     */
    Boolean saveDepartment(Department department);

    /**
     * 更新部门信息
     * @param department
     * @return
     */
    Integer updateDepartment(Department department);

    /**
     * 删除指定部门，逻辑删除
     * @param departmentIds
     * @return
     */
    Integer deleteDepartment(String departmentIds);

    /**
     * 查询指定部门信息
     * @param departmentQo
     * @return
     */
    List<Department> getBySearch(DepartmentQo departmentQo);

    /**
     * 查询指定Id的部门信息
     * @param departmentIds
     * @return
     */
    List<Department> getById(String departmentIds);

    /**
     * 查询登录用户的部门信息
     * @return
     */
    Department getLoginDepartment();

    /**
     * 保存或者更新部门信息
     * @param departmentQo
     * @return
     */
    Boolean saveOrUpdateDepartment(DepartmentQo departmentQo);

    Boolean getMyDepartment();
}

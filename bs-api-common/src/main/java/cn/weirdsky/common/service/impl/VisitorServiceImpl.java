package cn.weirdsky.common.service.impl;

import cn.weirdsky.common.entity.*;
import cn.weirdsky.common.entity.importExcel.VisitorImportExcel;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.entity.qo.VisitorQo;
import cn.weirdsky.common.mapper.DepartmentMapper;
import cn.weirdsky.common.mapper.VisitorMapper;
import cn.weirdsky.common.service.VisitorListService;
import cn.weirdsky.common.service.VisitorService;
import cn.weirdsky.common.util.*;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtill;
    @Autowired
    private PythonUtil pythonUtil;
    @Autowired
    private ZipUtil zipUtil;
    @Autowired
    private VisitorListService visitorListService;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Value(("${web.upload-path}"))
    private String uploadPath;

    public List<Visitor> getAll() {
        return visitorMapper.getAll();
    }

    @Override
    public boolean saveVisitor(Visitor visitor) {
        LoginUser user = loginUtil.getLoginUser();
        visitor.setCreateId(user.getUserId());
        visitor.setCreateName(user.getUserName());
        visitor.setCreateTime(new Date());
        visitor.setVisitorId(tableUtill.getTableId("visitor"));
        visitor.setDeleteMark("0");
        int insert = visitorMapper.insert(visitor);
        return insert == 1;
    }

    @Override
    public Integer updateVisitor(Visitor visitor) {
        QueryWrapper<Visitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("visitorId", visitor.getVisitorId());
        return visitorMapper.update(visitor, queryWrapper);
    }

    @Override
    public Integer deleteVisitor(String visitorIds) {
        String[] ids = visitorIds.split(",");
        QueryWrapper<Visitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("visitorId", ids);
        Visitor visitor = new Visitor();
        visitor.setDeleteMark("1");
        return visitorMapper.update(visitor, queryWrapper);
    }

    @Override
    public List<Visitor> getBySearch(VisitorQo visitorQo) {
        return null;
    }

    @Override
    public List<Visitor> getById(String visitorIds) {
        List<Visitor> visitors = new ArrayList<>();
        String[] ids = visitorIds.split(",");
        for (String id : ids) {
            if (stringUtil.IsNotEmpty(id)) {
                visitors.add(visitorMapper.selectById(id));
            }
        }
        return visitors;
    }

//    @Override
//    public List<VisitorVo> getByDepartmentIdAndDate(String departmentId, String date) {
//        QueryWrapper<Visitor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("deleteMark", 0);
//        if (loginUtil.getLoginUser().getUserType()=="1"){
//            queryWrapper.eq("departmentId", departmentId);
//        }
//        queryWrapper.like("")
//        return null;
//    }

    @Override
    public List<Visitor> getBySpace(VisitorSpaceQo space) {
        String listId = space.getListId();
        VisitorList visitorList = visitorListService.getOneByListId(listId);

        if (visitorList.getDepartmentIds() == null) {
            visitorList.setDepartmentIds("");
        }
        if (visitorList.getVisitorIds() == null) {
            visitorList.setVisitorIds("");
        }
        String[] departmentIds = visitorList.getDepartmentIds().split(",");
        QueryWrapper<Visitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.in("visitorDepartmentId", departmentIds);
        List<Visitor> listByDepartmentIds = visitorMapper.getListBySearch(queryWrapper);

        String[] visitorIds = visitorList.getVisitorIds().split(",");
        QueryWrapper<Visitor> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("deleteMark", "0");
        queryWrapper2.in("visitorId", visitorIds);
        List<Visitor> listByVisitorIds = visitorMapper.getListBySearch(queryWrapper2);

        HashSet<Visitor> visitors = new HashSet<>();
        visitors.addAll(listByDepartmentIds);
        visitors.addAll(listByVisitorIds);
        return visitors.stream().collect(Collectors.toList());
    }

    @Override
    public List<Visitor> getByDepartment(String departmentId) {
        QueryWrapper<Visitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.eq("departmentId", departmentId);
        return visitorMapper.getListBySearch(queryWrapper);
    }

    @Override
    public Boolean saveOrUpdateVisitor(MultipartFile file, String visitorName) {
        String fileName = visitorName + ".jpg";  //获取文件原名
        String visibleUri = "/" + fileName;     //拼接访问图片的地址
        String saveUri = uploadPath + "/pic/" + fileName;        //拼接保存图片的真实地址

        File saveFile = new File(saveUri);
        //判断是否存在文件夹，不存在就创建，但其实可以直接手动确定创建好，这样不用每次保存都检测
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //保存文件到真实存储路径下
            pythonUtil.callPythonScript("bs-api-train.py", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean getVisitorExcel(MultipartFile file) {
        String excelPath = uploadPath + "/excel/" + file.getOriginalFilename();
        File saveFile = new File(excelPath);
        //判断是否存在文件夹，不存在就创建，但其实可以直接手动确定创建好，这样不用每次保存都检测
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //保存文件到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        EasyExcel.read(excelPath, VisitorImportExcel.class, new PageReadListener<VisitorImportExcel>(dataList -> {
            for (VisitorImportExcel visitorImportExcel : dataList) {
                Visitor visitor = new Visitor();
                if (visitorImportExcel.getVisitorSex().equals("男")) {
                    visitor.setVisitorSex(0);
                } else if (visitorImportExcel.getVisitorSex().equals("女")) {
                    visitor.setVisitorSex(1);
                }
                visitor.setRemarks(visitorImportExcel.getRemarks());
                visitor.setVisitorName(visitorImportExcel.getVisitorName());
                visitor.setAttendanceDays(visitorImportExcel.getAttendanceDays());
                List<Department> departmentName = departmentMapper.getBySearch(new QueryWrapper<Department>().eq("departmentName", visitorImportExcel.getVisitorDepartmentName()));
                if (departmentName != null) {
                    visitor.setVisitorDepartmentId(departmentName.get(0).getDepartmentId());
                }
                saveVisitor(visitor);
            }
        })).sheet().doRead();
        return true;
    }

    @Override
    public Boolean getVisitorZip(MultipartFile file) {
        String zipPath = uploadPath + "/zip/" + file.getOriginalFilename();
        File saveFile = new File(zipPath);
        //判断是否存在文件夹，不存在就创建，但其实可以直接手动确定创建好，这样不用每次保存都检测
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //保存文件到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            zipUtil.unzipFile(zipPath, uploadPath + "\\pic\\");
            pythonUtil.callPythonScript("bs-api-train.py", "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}

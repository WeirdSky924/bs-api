package cn.weirdsky.visitor.service;

import cn.weirdsky.entity.entity.Visitor;
import cn.weirdsky.entity.entity.vo.DepartmentVo;
import cn.weirdsky.entity.entity.vo.VisitorLogVo;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VisitorService {

    /**
     * 获得所有登记人员的信息
     * @return
     */
    List<Visitor> getAll();

    /**
     * 保存登记人员信息
     * @param visitor
     * @return
     */
    boolean saveVisitor(Visitor visitor);

    /**
     * 更新登记人员信息
     * @param visitor
     * @return
     */
    Integer updateVisitor(Visitor visitor);

    /**
     * 删除登记人员信息
     * @param visitorIds
     * @return
     */
    Integer deleteVisitor(String visitorIds);

    /**
     * 获得指定要求的登记人员名单
     * @param queryWrapper
     * @return
     */
    List<Visitor> getBySearch(QueryWrapper queryWrapper);

    /**
     * 获得指定Id的登记人员名单
     * @param visitorIds
     * @return
     */
    List<Visitor> getById(String visitorIds);

//    List<VisitorVo> getByDepartmentIdAndDate(String departmentId, String date);

    /**
     * 获得指定访客空间的登记人员名单
     * @param space
     * @return
     */
    List<Visitor> getBySpace(VisitorSpaceQo space);

    /**
     * 获得指定部门的登记人员名单
     * @param departmentId
     * @return
     */
    List<Visitor> getByDepartment(String departmentId);

    /**
     * 单独登记一名人员
     * @param file
     * @param visitorName
     * @return
     */
    Boolean saveOrUpdateVisitor(MultipartFile file, String visitorName);

    Boolean getVisitorExcel(MultipartFile file);

    Boolean getVisitorZip(MultipartFile file);

    List<VisitorLogVo> visitorStatisticsGroupByDate();

    Visitor getByVisitorName(String visitorName);

    List<DepartmentVo> visitorStatisticGroupByDepartment();

    List<Visitor> getVisitorByDepartmentName(String departmentName);

    List<Visitor> getListBySearch(String queryWrapper);
}

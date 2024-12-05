package cn.weirdsky.common.service;

import cn.weirdsky.common.entity.Visitor;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.entity.qo.VisitorQo;
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
     * @param visitorQo
     * @return
     */
    List<Visitor> getBySearch(VisitorQo visitorQo);

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
}

package cn.weirdsky.visitor.service;


import cn.weirdsky.entity.entity.VisitorSpace;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface VisitorSpaceService {

    /**
     * 获得所有访客空间信息
     * @return
     */
    List<VisitorSpaceQo> getAll();

    /**
     * 保存访客空间信息
     * @param visitorSpace
     * @return
     */
    Boolean saveVisitorSpace(VisitorSpaceQo visitorSpace);

    /**
     * 保存访客空间信息携带listId
     * @param visitorSpace
     * @param listId
     * @return
     */
    Boolean saveVisitorSpace(VisitorSpace visitorSpace, String listId);

    /**
     * 更新访客空间信息
     * @param visitorSpace
     * @return
     */
    Integer updateVisitorSpace(VisitorSpace visitorSpace);

    /**
     * 删除访客空间，逻辑删除
     * @param spaceIds
     * @return
     */
    Integer deleteVisitorSpace(String spaceIds);

    /**
     * 获得指定要求的访客空间
     * @param spaceVo
     * @return
     */
    List<VisitorSpaceQo> getBySearch(VisitorSpaceQo spaceVo);

    /**
     * 获得指定Id的访客空间
     * @param spaceIds
     * @return
     */
    List<VisitorSpaceQo> getById(String spaceIds);

    /**
     * 获得登录人员管理的访客空间
     * @return
     */
    List<VisitorSpaceQo> getLoginList();

    /**
     * 保存或者更新访客空间
     * @param visitorSpaceQo
     * @return
     */
    Boolean saveOrUpdateSpace(VisitorSpaceQo visitorSpaceQo);

    List<VisitorSpaceQo> getMySpace();

    List<VisitorSpaceQo> getListBySearch(String queryWrapper);
}

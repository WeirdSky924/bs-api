package cn.weirdsky.common.service;

import cn.weirdsky.common.entity.VisitorList;

import java.util.List;

public interface VisitorListService {

    /**
     * 保存监察访问清单并获得保存后listId
     * @param visitorList
     * @return
     */
    String saveListAndGetId(VisitorList visitorList);

    /**
     * 获得所有监察访问清单信息
     * @return
     */
    List<VisitorList> getAll();

    /**
     * 更新监察访问清单信息
     * @param visitorList
     * @return
     */
    Integer updateList(VisitorList visitorList);

    /**
     * 删除指定监察访问清单，逻辑删除
     * @param listIds
     * @return
     */
    Integer deleteList(String listIds);

    /**
     * 获得指定Id监察访问清单
     * @param listIds
     * @return
     */
    List<VisitorList> getById(String listIds);

    /**
     * 获得一条指定Id监察访问清单信息
     * @param listId
     * @return
     */
    VisitorList getOneByListId(String listId);

}

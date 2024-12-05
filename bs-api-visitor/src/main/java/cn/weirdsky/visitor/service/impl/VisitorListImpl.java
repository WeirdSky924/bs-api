package cn.weirdsky.visitor.service.impl;

import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.VisitorList;
import cn.weirdsky.mapper.mapper.VisitorListMapper;
import cn.weirdsky.visitor.service.VisitorListService;
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
public class VisitorListImpl implements VisitorListService {

    @Autowired
    private VisitorListMapper visitorListMapper;
    @Autowired
    private TableUtil tableUtill;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;

    @Override
    public String saveListAndGetId(VisitorList visitorList) {
        String listId = tableUtill.getTableId("visitor_list");
        LoginUser loginUser = loginUtil.getLoginUser();
        visitorList.setListId(listId);
        visitorList.setCreateId(loginUser.getUserId());
        visitorList.setCreateName(loginUser.getUserName());
        visitorList.setCreateTime(new Date());
        visitorList.setDeleteMark("0");
        visitorListMapper.insert(visitorList);
        return listId;
    }

    public List<VisitorList> getAll(){
        return visitorListMapper.getAll();
    }

    @Override
    public Integer updateList(VisitorList visitorList) {
        QueryWrapper<VisitorList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.eq("listId", visitorList.getListId());
        return visitorListMapper.update(visitorList, queryWrapper);
    }

    @Override
    public Integer deleteList(String listIds) {
        String[] ids = listIds.split(",");
        QueryWrapper<VisitorList> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("listId", ids);
        VisitorList visitorList = new VisitorList();
        visitorList.setDeleteMark("1");
        return visitorListMapper.update(visitorList, queryWrapper);
    }

    @Override
    public List<VisitorList> getById(String listIds) {
        List<VisitorList> visitorLists = new ArrayList<>();
        String[] ids = listIds.split(",");
        for (String id: ids) {
            if (stringUtil.IsNotEmpty(id)){
                visitorLists.add(visitorListMapper.selectById(id));
            }
        }
        return visitorLists;
    }

    @Override
    public VisitorList getOneByListId(String listId) {
        QueryWrapper<VisitorList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.eq("listId", listId);
        return visitorListMapper.selectOne(queryWrapper);
    }

}

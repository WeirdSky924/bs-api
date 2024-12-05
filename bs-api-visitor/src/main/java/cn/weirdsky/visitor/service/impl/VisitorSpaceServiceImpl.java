package cn.weirdsky.visitor.service.impl;

import cn.weirdsky.entity.entity.*;
import cn.weirdsky.entity.entity.qo.VisitorLogQo;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import cn.weirdsky.mapper.mapper.VisitorLogMapper;
import cn.weirdsky.mapper.mapper.VisitorSpaceMapper;
import cn.weirdsky.visitor.service.VisitorListService;
import cn.weirdsky.visitor.service.VisitorSpaceService;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VisitorSpaceServiceImpl implements VisitorSpaceService {

    @Autowired
    private VisitorListService visitorListService;
    @Autowired
    private VisitorSpaceMapper visitorSpaceMapper;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private TableUtil tableUtill;

    @Override
    public List<VisitorSpaceQo> getAll() {
        return visitorSpaceMapper.getAll();
    }

    @Override
    public Boolean saveVisitorSpace(VisitorSpaceQo visitorSpace) {
        VisitorList visitorList = new VisitorList();
        visitorList.setDepartmentIds(visitorSpace.getDepartmentIds() == null ? "" : visitorSpace.getDepartmentIds());
        visitorList.setVisitorIds(visitorSpace.getVisitorIds() == null ? "" : visitorSpace.getVisitorIds());
        String listId = visitorListService.saveListAndGetId(visitorList);
        return saveVisitorSpace(visitorSpace, listId);
    }

    @Override
    public Boolean saveVisitorSpace(VisitorSpace visitorSpace, String listId) {
        if (stringUtil.IsNotEmpty(listId)) {
            visitorSpace.setListId(listId);
        }
        LoginUser user = loginUtil.getLoginUser();
        visitorSpace.setCreateId(user.getUserId());
        visitorSpace.setCreateName(user.getUserName());
        visitorSpace.setCreateTime(new Date());
        visitorSpace.setSpaceId(tableUtill.getTableId("visitor_space"));
        visitorSpace.setDeleteMark("0");
        visitorSpace.setSpaceEnable("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        visitorSpace.setSpaceStartTime(visitorSpace.getSpaceStartTime());
        visitorSpace.setSpaceEndTime(visitorSpace.getSpaceEndTime());
        return visitorSpaceMapper.insert(visitorSpace) == 1;
    }

    @Override
    public Integer updateVisitorSpace(VisitorSpace visitorSpace) {
        return visitorSpaceMapper.update(visitorSpace, new QueryWrapper<VisitorSpace>().eq("spaceId", visitorSpace.getSpaceId()));
    }

    @Override
    public Integer deleteVisitorSpace(String spaceIds) {
        String[] ids = spaceIds.split(",");
        QueryWrapper<VisitorSpace> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("spaceId", ids);
        VisitorSpace visitorSpace = new VisitorSpace();
        visitorSpace.setDeleteMark("1");
        return visitorSpaceMapper.update(visitorSpace, queryWrapper);
    }

    @Override
    public List<VisitorSpaceQo> getBySearch(VisitorSpaceQo spaceVo) {
        QueryWrapper<VisitorSpace> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(stringUtil.IsNotEmpty(spaceVo.getSpaceName()), "spaceName", spaceVo.getSpaceName());
        if (stringUtil.IsNotEmpty(spaceVo.getStartTime()) && stringUtil.IsNotEmpty(spaceVo.getEndTime())) {
            queryWrapper.between("spaceStartTime", spaceVo.getStartTime(), spaceVo.getEndTime())
                    .between("spaceEndTime", spaceVo.getStartTime(), spaceVo.getEndTime());
        }
        queryWrapper.like(stringUtil.IsNotEmpty(spaceVo.getSearch()), "spaceName", spaceVo.getSearch());
        queryWrapper.eq(stringUtil.IsNotEmpty(spaceVo.getSpaceDepartmentId()), "spaceDepartmentId", spaceVo.getSpaceDepartmentId());
        queryWrapper.eq(stringUtil.IsNotEmpty(spaceVo.getSpaceEnable()), "spaceEnable", spaceVo.getSpaceEnable());
        queryWrapper.eq("deleteMark", "0");
        return visitorSpaceMapper.getListBySearch(queryWrapper);
    }

    @Override
    public List<VisitorSpaceQo> getById(String spaceIds) {
        String[] ids = spaceIds.split(",");
        QueryWrapper<VisitorSpace> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(stringUtil.IsNotEmpty(ids), "spaceId", ids);
        queryWrapper.eq("deleteMark", "0");
        List<VisitorSpaceQo> spaces = visitorSpaceMapper.getById(queryWrapper);
        return spaces;
    }

    @Override
    public List<VisitorSpaceQo> getLoginList() {
        LoginUser user = loginUtil.getLoginUser();
        String userId = user.getUserId();
        if (user.getUserType() == "0") {
            return visitorSpaceMapper.getAll();
        }
        return visitorSpaceMapper.getLoginList(userId);
    }

    @Override
    public Boolean saveOrUpdateSpace(VisitorSpaceQo visitorSpaceQo) {
        if (stringUtil.IsEmpty(visitorSpaceQo.getSpaceId())) {
            return saveVisitorSpace(visitorSpaceQo);
        }
        if (visitorSpaceMapper.selectById(visitorSpaceQo.getSpaceId()) == null) {
            return false;
        }
        return updateVisitorSpace(visitorSpaceQo) == 1;
    }

    @Override
    public List<VisitorSpaceQo> getMySpace() {
        LoginUser loginUser = loginUtil.getLoginUser();
        if (loginUser.getUserType().equals("0")) {
            return getAll();
        }
        QueryWrapper<VisitorSpaceQo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(stringUtil.IsNotEmpty(loginUser.getUserId()), "spaceAdminUserIds", loginUser.getUserId());
        List<VisitorSpaceQo> space = visitorSpaceMapper.getMySpace(queryWrapper);
        return space;
    }

    @Override
    public List<VisitorSpaceQo> getListBySearch(String queryWrapper) {
        return visitorSpaceMapper.getListBySearchString(queryWrapper);
    }

}

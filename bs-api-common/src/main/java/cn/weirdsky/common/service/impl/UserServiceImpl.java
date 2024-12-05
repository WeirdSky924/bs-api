package cn.weirdsky.common.service.impl;

import cn.weirdsky.common.entity.LoginUser;
import cn.weirdsky.common.entity.User;
import cn.weirdsky.common.entity.qo.UserQo;
import cn.weirdsky.common.mapper.UserMapper;
import cn.weirdsky.common.service.UserService;
import cn.weirdsky.common.util.LoginUtil;
import cn.weirdsky.common.util.StringUtil;
import cn.weirdsky.common.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtill;

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public Boolean saveUser(User user) {
        user.setCreateTime(new Date());
        user.setUserId(tableUtill.getTableId("user"));
        user.setDeleteMark("0");
        user.setUserType("1");
        int insert = userMapper.insert(user);
        return insert == 1;
    }

    @Override
    public Integer updateUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.eq("userId", user.getUserId());
        queryWrapper.eq("userCode", user.getUserCode());
        return userMapper.update(user, queryWrapper);
    }

    @Override
    public Integer deleteUser(String userIds) {
        String[] ids = userIds.split(",");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("userId", ids);
        User user = new User();
        user.setDeleteMark("1");
        return userMapper.update(user, queryWrapper);
    }

    @Override
    public List<User> getById(String userIds) {
        List<User> users = new ArrayList<>();
        String[] ids = userIds.split(",");
        for (String id : ids) {
            if (stringUtil.IsNotEmpty(id)) {
                users.add(userMapper.selectById(id));
            }
        }
        return users;
    }

    @Override
    public Boolean getByCode(String userCode) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq(stringUtil.IsNotEmpty(userCode), "userCode", userCode));
        return user != null;
    }

    @Override
    public Boolean alterUser(UserQo userQo) {
        Integer user = 0;
        LoginUser loginUser = loginUtil.getLoginUser();
        if (userQo.getConfirmPassword().equals(userQo.getPassword()) &&
                (loginUser.getUserCode().equals(userQo.getUserCode()) || loginUser.getUserType().equals("0"))) {
            user = updateUser(userQo);
        }
        return user == 1;
    }

    @Override
    public LoginUser getInfo() {
        return loginUtil.getLoginUser();
    }
}

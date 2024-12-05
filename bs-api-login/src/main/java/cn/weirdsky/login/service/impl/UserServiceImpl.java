package cn.weirdsky.login.service.impl;

import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.User;
import cn.weirdsky.entity.entity.qo.UserQo;
import cn.weirdsky.mapper.mapper.UserMapper;
import cn.weirdsky.login.service.UserService;
import cn.weirdsky.utils.util.DynamicDataSourceUtil;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DynamicDataSourceUtil dynamicDataSourceUtil;

    @Override
    public List<User> getAll() {
//        dynamicDataSourceUtil.setDataSource("user");
        List<User> users = userMapper.getAll();
//        dynamicDataSourceUtil.clearDataSource();
        return users;
    }

    @Override
    public Boolean saveUser(User user) {
        user.setCreateTime(new Date());
        user.setUserId(tableUtill.getTableId("user"));
        user.setDeleteMark("0");
        user.setUserType("1");
//        dynamicDataSourceUtil.setDataSource("user");
        int insert = userMapper.insert(user);
//        dynamicDataSourceUtil.clearDataSource();
        return insert == 1;
    }

    @Override
    public Integer updateUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.eq("userId", user.getUserId());
        queryWrapper.eq("userCode", user.getUserCode());
//        dynamicDataSourceUtil.setDataSource("user");
        int update = userMapper.update(user, queryWrapper);
//        dynamicDataSourceUtil.clearDataSource();
        return update;
    }

    @Override
    public Integer deleteUser(String userIds) {
        String[] ids = userIds.split(",");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("userId", ids);
        User user = new User();
        user.setDeleteMark("1");
//        dynamicDataSourceUtil.setDataSource("user");
        int update = userMapper.update(user, queryWrapper);
//        dynamicDataSourceUtil.clearDataSource();
        return update;
    }

    @Override
    public List<User> getById(String userIds) {
        List<User> users = new ArrayList<>();
        String[] ids = userIds.split(",");
        for (String id : ids) {
            if (stringUtil.IsNotEmpty(id)) {
//                dynamicDataSourceUtil.setDataSource("user");
                users.add(userMapper.selectById(id));
//                dynamicDataSourceUtil.clearDataSource();
            }
        }
        return users;
    }

    @Override
    public Boolean getByCode(String userCode) {
//        dynamicDataSourceUtil.setDataSource("user");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq(stringUtil.IsNotEmpty(userCode), "userCode", userCode));
//        dynamicDataSourceUtil.clearDataSource();
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

    @Override
    public boolean valSession(String sessionId) {
        if (stringUtil.IsNotEmpty(sessionId)) {
            Object o = redisTemplate.opsForValue().get(sessionId);
            if (o != null) {
                return true;
            }
        }
        return false;
    }
}

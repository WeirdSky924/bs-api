package cn.weirdsky.login.service.impl;

import cn.weirdsky.entity.entity.User;
import cn.weirdsky.entity.entity.qo.UserQo;
import cn.weirdsky.mapper.mapper.UserMapper;
import cn.weirdsky.login.service.LoginService;
import cn.weirdsky.login.service.UserService;
import cn.weirdsky.utils.util.DynamicDataSourceUtil;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TableUtil tableUtil;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private DynamicDataSourceUtil dynamicDataSourceUtil;
    @Autowired
    private UserService userService;

    public Boolean getLogin(UserQo userQo){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userCode", userQo.getUserCode());
        queryWrapper.eq("deleteMark", 0);
        queryWrapper.eq("password", userQo.getPassword());
//        dynamicDataSourceUtil.setDataSource("user");
        User login = userMapper.getLogin(queryWrapper);
//        dynamicDataSourceUtil.clearDataSource();
        return loginUtil.setLoginUser(login);
    }

    @Override
    public Boolean logOut(HttpSession session) {
        if (stringUtil.IsEmpty(session.getId())) {
            return false;
        }
        session.invalidate();
        return loginUtil.setLoginUser();
    }

    @Override
    public Boolean regUser(UserQo userQo) {
        Boolean code = userService.getByCode(userQo.getUserCode());
        if (userQo.getConfirmPassword().equals(userQo.getPassword()) && !code) {
            userService.saveUser(userQo);
            return true;
        }
        return false;
    }

}

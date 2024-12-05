package cn.weirdsky.common.service.impl;

import cn.weirdsky.common.entity.User;
import cn.weirdsky.common.entity.qo.UserQo;
import cn.weirdsky.common.mapper.UserMapper;
import cn.weirdsky.common.service.LoginService;
import cn.weirdsky.common.service.UserService;
import cn.weirdsky.common.util.LoginUtil;
import cn.weirdsky.common.util.StringUtil;
import cn.weirdsky.common.util.TableUtil;
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
    private UserService userService;

    public Boolean getLogin(UserQo userQo){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userCode", userQo.getUserCode());
        queryWrapper.eq("deleteMark", 0);
        queryWrapper.eq("password", userQo.getPassword());
        User login = userMapper.getLogin(queryWrapper);
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

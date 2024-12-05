package cn.weirdsky.common.util.impl;

import cn.weirdsky.common.entity.LoginUser;
import cn.weirdsky.common.entity.User;
import cn.weirdsky.common.util.LoginUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginUtilImpl implements LoginUtil {

    private static LoginUser loginUser = new LoginUser();

    @Override
    public LoginUser getLoginUser() {
        return loginUser;
    }

    @Override
    public Boolean setLoginUser() {
        loginUser = new LoginUser();
        return true;
    }

    @Override
    public Boolean setLoginUser(User user) {
        if (user == null) {
            return false;
        }
        loginUser.setLoginTime(new Date());
        loginUser.setUserId(user.getUserId());
        loginUser.setUserName(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setUserCode(user.getUserCode());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setEmail(user.getEmail());
        loginUser.setPhone(user.getPhone());
        return true;
    }

    @Override
    public Boolean setLoginUser(LoginUser user) {
        if (user == null) {
            return false;
        }
        loginUser = user;
        return true;
    }
}

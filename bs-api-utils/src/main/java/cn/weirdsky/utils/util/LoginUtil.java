package cn.weirdsky.utils.util;


import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.User;

public interface LoginUtil {

    /**
     * 获得登录用户
     * @return
     */
    LoginUser getLoginUser();

    /**
     * 设置空登录用户
     * @return
     */
    Boolean setLoginUser();

    /**
     * 设置登录用户
     * @param loginUser
     * @return
     */
    Boolean setLoginUser(User loginUser);

    /**
     * 设置登录用户
     * @param user
     * @return
     */
    Boolean setLoginUser(LoginUser user);
}

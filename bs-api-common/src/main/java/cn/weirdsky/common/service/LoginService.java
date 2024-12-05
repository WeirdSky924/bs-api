package cn.weirdsky.common.service;

import cn.weirdsky.common.entity.qo.UserQo;
import jakarta.servlet.http.HttpSession;

public interface LoginService {

    /**
     * 判断用户登录是否成功
     * @param userQo
     * @return
     */
    Boolean getLogin(UserQo userQo);

    /**
     * 登出当前用户
     * @return
     */
    Boolean logOut(HttpSession Session);

    /**
     * 注册用户
     * @param userQo
     * @return
     */
    Boolean regUser(UserQo userQo);
}

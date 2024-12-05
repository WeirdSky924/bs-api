package cn.weirdsky.login.service;

import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.User;
import cn.weirdsky.entity.entity.qo.UserQo;

import java.util.List;

public interface UserService {

    /**
     * 获得所有账户信息
     * @return
     */
    List<User> getAll();

    /**
     * 保存指定账户
     * @param user
     * @return
     */
    Boolean saveUser(User user);

    /**
     * 更新指定账户信息
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 删除指定账户信息
     * @param userIds
     * @return
     */
    Integer deleteUser(String userIds);

    /**
     * 获得指定Id的账户
     * @param userIds
     * @return
     */
    List<User> getById(String userIds);

    /**
     * 是否存在指定Code的账户
     * @param userCode
     * @return
     */
    Boolean getByCode(String userCode);

    /**
     * 修改指定账户信息，最终调用updateUser更新
     * @param userQo
     * @return
     */
    Boolean alterUser(UserQo userQo);

    /**
     * 获得当前登录用户信息
     * @return
     */
    LoginUser getInfo();

    boolean valSession(String sessionId);
}

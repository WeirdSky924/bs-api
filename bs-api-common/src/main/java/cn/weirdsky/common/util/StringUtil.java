package cn.weirdsky.common.util;

import cn.weirdsky.common.entity.LoginUser;

import java.util.Date;

public interface StringUtil {

    /**
     * 判断是否空字符串
     * @param string
     * @return
     */
    Boolean IsNotEmpty(String string);

    /**
     * 判断是否空字符串数组
     * @param string
     * @return
     */
    Boolean IsNotEmpty(String[] string);

    /**
     * 获得Token
     * @param user
     * @return
     */
    String getToken(LoginUser user);

    /**
     * 判断是否空字符串
     * @param string
     * @return
     */
    boolean IsEmpty(String string);

    /**
     * 判断是否空字符串数组
     * @param string
     * @return
     */
    boolean IsEmpty(String[] string);

    /**
     * 获得星期几
     * @param date
     * @return
     */
    String getWeek(Date date);
}

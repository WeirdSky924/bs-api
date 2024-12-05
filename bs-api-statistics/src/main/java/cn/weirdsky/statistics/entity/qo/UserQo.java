package cn.weirdsky.statistics.entity.qo;

import cn.weirdsky.statistics.entity.User;
import lombok.Data;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Data
public class UserQo extends User {

    private String confirmPassword;
    private HttpSession session;
    private Cookie cookie;
    private String userIds;

}

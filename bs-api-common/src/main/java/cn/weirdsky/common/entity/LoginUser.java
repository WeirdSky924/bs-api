package cn.weirdsky.common.entity;


import lombok.Data;

import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 3824756829345678238L;

    private String userId;
    private String userCode;
    private String userName;
    private String userType;
    private String phone;
    private String email;
    private String deptId;
    private Date LoginTime;
    private String token;
//    private HttpSession session;

}

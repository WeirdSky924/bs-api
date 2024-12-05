package cn.weirdsky.visitor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {

    @TableId("userId")
    private String userId;
    @TableField("userCode")
    private String userCode;
    @TableField("userName")
    private String userName;
    @TableField("userType")
    private String userType;
    @TableField("password")
    private String password;
    @TableField("phone")
    private String phone;
    @TableField("email")
    private String email;
    @TableField("deptId")
    private String deptId;
    @TableField("remarks")
    private String remarks;
    @TableField("createId")
    private String createId;
    @TableField("createName")
    private String createName;
    @TableField("createTime")
    private Date createTime;
    @TableField("deleteMark")
    private String deleteMark;

}

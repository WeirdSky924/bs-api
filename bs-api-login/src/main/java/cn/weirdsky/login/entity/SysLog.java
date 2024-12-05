package cn.weirdsky.login.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_log")
public class SysLog implements Serializable {

    @TableId("sysLogId")
    private String sysLogId;

    @TableField("userId")
    private String userId;

    @TableField("methodName")
    private String methodName;

    @TableField("methodParams")
    private String methodParams;

    @TableField("info")
    private String info;

    @TableField("time")
    private Date time;

    @TableField("deleteMark")
    private String deleteMark;


}

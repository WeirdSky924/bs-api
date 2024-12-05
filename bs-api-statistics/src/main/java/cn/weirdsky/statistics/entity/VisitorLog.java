package cn.weirdsky.statistics.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("visitor_log")
public class VisitorLog implements Serializable {

    @TableId("visitorLogId")
    private String visitorLogId;

    @TableField("visitorId")
    private String visitorId;

    @TableField("visitorTimeIn")
    private Date visitorTimeIn;

    @TableField("visitorTimeOut")
    private Date visitorTimeOut;

    @TableField("visitorTime")
    private Date visitorTime;

    @TableField("deleteMark")
    private String deleteMark;

    @TableField("pic")
    private byte[] pic;

}

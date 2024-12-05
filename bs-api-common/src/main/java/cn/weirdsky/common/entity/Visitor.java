package cn.weirdsky.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("visitor")
public class Visitor implements Serializable {

    @TableId("visitorId")
    private String visitorId;
    @TableField("visitorName")
    private String visitorName;
    @TableField("visitorSex")
    private Integer visitorSex;
    @TableField("visitorDepartmentId")
    private String visitorDepartmentId;
    @TableField("attendanceDays")
    private String attendanceDays;
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

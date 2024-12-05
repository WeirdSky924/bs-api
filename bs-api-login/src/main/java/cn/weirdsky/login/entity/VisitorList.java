package cn.weirdsky.login.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("visitor_list")
public class VisitorList implements Serializable {

    @TableId("listId")
    private String listId;
    @TableField("departmentIds")
    private String departmentIds;
    @TableField("visitorIds")
    private String visitorIds;
    @TableField("remarks")
    private String remarks;
    @TableField("UpdateId")
    private String UpdateId;
    @TableField("UpdateName")
    private String UpdateName;
    @TableField("updateTime")
    private Date updateTime;
    @TableField("createId")
    private String createId;
    @TableField("createName")
    private String createName;
    @TableField("createTime")
    private Date createTime;
    @TableField("deleteMark")
    private String deleteMark;

}

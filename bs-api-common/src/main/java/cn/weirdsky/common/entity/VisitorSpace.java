package cn.weirdsky.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("visitor_space")
public class VisitorSpace implements Serializable {

    @TableId("spaceId")
    private String spaceId;

    @TableField("spaceName")
    private String spaceName;

    @TableField("spaceDepartmentId")
    private String spaceDepartmentId;

    @TableField("spaceAdminUserIds")
    private String spaceAdminUserIds;

    @TableField("spaceStartTime")
    private Date spaceStartTime;

    @TableField("spaceEndTime")
    private Date spaceEndTime;

    @TableField("spaceEnable")
    private String spaceEnable;

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

    @TableField("listId")
    private String listId;

}

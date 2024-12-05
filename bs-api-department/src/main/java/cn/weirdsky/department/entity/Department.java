package cn.weirdsky.department.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("department")
public class Department implements Serializable {

    @TableId("departmentId")
    private String departmentId;
    @TableField("departmentName")
    private String departmentName;
    @TableField("departmentAdminUserIds")
    private String departmentAdminUserIds;
    @TableField("departmentPhone")
    private String departmentPhone;
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

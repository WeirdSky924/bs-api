package cn.weirdsky.department.entity.vo;

import cn.weirdsky.department.entity.Department;
import lombok.Data;

@Data
public class DepartmentVo extends Department {

    private String departmentName;

    private Integer visitorCount;

}

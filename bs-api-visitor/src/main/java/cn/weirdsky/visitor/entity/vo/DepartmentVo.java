package cn.weirdsky.visitor.entity.vo;

import cn.weirdsky.visitor.entity.Department;
import lombok.Data;

@Data
public class DepartmentVo extends Department {

    private String departmentName;

    private Integer visitorCount;

}

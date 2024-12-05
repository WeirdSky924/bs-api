package cn.weirdsky.entity.entity.vo;

import cn.weirdsky.entity.entity.Department;
import lombok.Data;

@Data
public class DepartmentVo extends Department {

    private String departmentName;

    private Integer visitorCount;

}

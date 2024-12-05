package cn.weirdsky.statistics.entity.vo;

import cn.weirdsky.statistics.entity.Department;
import lombok.Data;

@Data
public class DepartmentVo extends Department {

    private String departmentName;

    private Integer visitorCount;

}

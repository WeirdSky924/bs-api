package cn.weirdsky.common.entity.vo;

import cn.weirdsky.common.entity.Department;
import lombok.Data;

@Data
public class DepartmentVo extends Department {

    private String departmentName;

    private Integer visitorCount;

}

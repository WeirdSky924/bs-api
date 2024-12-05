package cn.weirdsky.login.entity.vo;

import cn.weirdsky.login.entity.Department;
import lombok.Data;

@Data
public class DepartmentVo extends Department {

    private String departmentName;

    private Integer visitorCount;

}

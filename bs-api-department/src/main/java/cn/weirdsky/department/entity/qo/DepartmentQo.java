package cn.weirdsky.department.entity.qo;

import cn.weirdsky.department.entity.Department;
import lombok.Data;

@Data
public class DepartmentQo extends Department {

    private Double attendanceRatio;
    private Integer peopleNum;
    private String search;
    private String startTime;
    private String endTime;
    private String Time;
    private String departmentIds;

}

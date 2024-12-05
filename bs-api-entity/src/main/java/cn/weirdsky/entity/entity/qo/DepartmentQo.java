package cn.weirdsky.entity.entity.qo;

import cn.weirdsky.entity.entity.Department;
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

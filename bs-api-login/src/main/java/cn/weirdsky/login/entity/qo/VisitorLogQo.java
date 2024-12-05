package cn.weirdsky.login.entity.qo;

import cn.weirdsky.login.entity.VisitorLog;
import lombok.Data;

@Data
public class VisitorLogQo extends VisitorLog {

    private String startTime;
    private String endTime;
    private String search;
    private String Time;
    private String visitorDepartmentId;
    private String visitorName;
    private String attendanceDays;
    private String logIds;
    private Boolean isLegal;
    private Integer count;

}

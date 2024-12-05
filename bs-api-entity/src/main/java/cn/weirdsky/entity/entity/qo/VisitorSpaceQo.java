package cn.weirdsky.entity.entity.qo;

import cn.weirdsky.entity.entity.VisitorSpace;
import lombok.Data;

@Data
public class VisitorSpaceQo extends VisitorSpace {

    private String search;
    private Integer absentNum;
    private Integer presentNum;
    private Integer totalNum;
    private Double attendanceRatio;
    private String departmentIds;
    private String visitorIds;
    private String startTime;
    private String endTime;
    private String Time;
    private String spaceIds;

}

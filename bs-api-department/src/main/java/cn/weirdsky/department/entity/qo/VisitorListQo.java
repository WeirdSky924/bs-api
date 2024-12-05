package cn.weirdsky.department.entity.qo;

import cn.weirdsky.department.entity.VisitorList;
import lombok.Data;

@Data
public class VisitorListQo extends VisitorList {
    private String search;
    private String startTime;
    private String endTime;
    private String Time;
}

package cn.weirdsky.visitor.entity.qo;

import cn.weirdsky.visitor.entity.VisitorList;
import lombok.Data;

@Data
public class VisitorListQo extends VisitorList {
    private String search;
    private String startTime;
    private String endTime;
    private String Time;
}

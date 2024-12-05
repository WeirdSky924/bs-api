package cn.weirdsky.common.entity.qo;

import cn.weirdsky.common.entity.VisitorList;
import lombok.Data;

@Data
public class VisitorListQo extends VisitorList {
    private String search;
    private String startTime;
    private String endTime;
    private String Time;
}

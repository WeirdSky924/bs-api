package cn.weirdsky.entity.entity.qo;

import cn.weirdsky.entity.entity.Visitor;
import lombok.Data;

@Data
public class VisitorQo extends Visitor {
    private String search;
    private String startTime;
    private String endTime;
    private String Time;
    private Integer isPresent;// 0缺席 1出席 2无需出席
    private String visitorIds;
}

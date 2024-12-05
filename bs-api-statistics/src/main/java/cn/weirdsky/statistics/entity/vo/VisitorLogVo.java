package cn.weirdsky.statistics.entity.vo;

import cn.weirdsky.statistics.entity.VisitorLog;
import lombok.Data;

import java.util.Date;

@Data
public class VisitorLogVo extends VisitorLog {

    private String visitorName;

    private String year;

    private String month;

    private String sum;

    private Date visitorTime;

}

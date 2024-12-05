package cn.weirdsky.entity.entity.vo;

import cn.weirdsky.entity.entity.VisitorLog;
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

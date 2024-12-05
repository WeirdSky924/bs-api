package cn.weirdsky.common.entity.vo;

import cn.weirdsky.common.entity.VisitorLog;
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

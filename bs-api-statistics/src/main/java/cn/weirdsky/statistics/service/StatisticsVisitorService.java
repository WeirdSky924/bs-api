package cn.weirdsky.statistics.service;


import java.util.HashMap;
import java.util.List;

public interface StatisticsVisitorService {

    /**
     * 统计整体访客情况
     * 处理出：每月出入次数，近七日考勤率
     *
     * @return
     */
    HashMap<String, List<HashMap<String, String>>> visitorStatistics();

    /**
     * 按部门统计访客情况，总考情率，总人数，总出入次数
     *
     * @return
     */
    HashMap<String, HashMap<String, String>> visitorByDepartmentStatistics();

    /**
     * 按访客空间统计访客情况
     * 首先获取名单，再从访客LOG中确定出席状态
     *
     * @return
     */
    HashMap<String, HashMap<String, String>> visitorBySpaceStatistics();

}

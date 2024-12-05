package cn.weirdsky.common.controller;

import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.service.StatisticsVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsVisitorService statisticsVisitorService;

    @GetMapping("/getVisitorStatistics")
    public R getVisitorStatistics() {
        return R.ok(statisticsVisitorService.visitorStatistics());
    }

    @GetMapping("/getVisitorByDepartmentStatistics")
    public R getVisitorByDepartmentStatistics() {
        return R.ok(statisticsVisitorService.visitorByDepartmentStatistics());
    }

    @GetMapping("/getVisitorBySpaceStatistics")
    public R getVisitorBySpaceStatistics() {
        return R.ok(statisticsVisitorService.visitorBySpaceStatistics());
    }

}

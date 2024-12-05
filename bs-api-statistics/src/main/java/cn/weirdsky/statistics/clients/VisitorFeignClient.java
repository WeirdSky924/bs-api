package cn.weirdsky.statistics.clients;

import cn.weirdsky.entity.entity.R;
import cn.weirdsky.entity.entity.Visitor;
import cn.weirdsky.entity.entity.VisitorLog;
import cn.weirdsky.entity.entity.qo.VisitorLogQo;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import cn.weirdsky.entity.entity.vo.DepartmentVo;
import cn.weirdsky.entity.entity.vo.VisitorLogVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("visitor")
public interface VisitorFeignClient {

    @GetMapping("/visitor/visitorStatisticsGroupByDate")
    R visitorStatisticsGroupByDate();

    @PostMapping("/visitor/getByVisitorName")
    R getByVisitorName(@RequestParam("visitor")String visitorName);

    @PostMapping("/visitor_log/getBySearchQuery")
    R getBySearch(@RequestParam("queryWrapper") String queryWrapper);

    @GetMapping("/visitor/visitorStatisticGroupByDepartment")
    R visitorStatisticGroupByDepartment();

    @PostMapping("/visitor/getVisitorByDepartmentName")
    R getVisitorByDepartmentName(@RequestParam("departmentName") String departmentName);

    @PostMapping("/visitor_space/getListBySearch")
    R getListBySearchSpace(@RequestParam("queryWrapper") String eq);

    @PostMapping("/visitor_log/getLogBySpaceQuery")
    R getLogBySpace(@RequestParam("queryWrapper") String queryWrapper);

    @PostMapping("/visitor/getListBySearch")
    R getListBySearchVisitor(@RequestParam("queryWrapper") String queryWrapper2);
}

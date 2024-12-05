package cn.weirdsky.statistics.clients;

import cn.weirdsky.entity.entity.R;
import cn.weirdsky.entity.entity.vo.DepartmentVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("department")
public interface DepartmentFeignClient {

    @GetMapping("/department/getVisitorLogDepartmentVo")
    R getVisitorLogDepartmentVo();
}

package cn.weirdsky.visitor.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("visitor")
public interface VisitorFeignClient {


}

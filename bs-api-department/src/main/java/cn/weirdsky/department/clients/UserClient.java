package cn.weirdsky.department.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("login")
public interface UserClient {

    @PostMapping("/user/valSession")
    boolean valSession(@RequestParam("sessionId")String sessionId);

}

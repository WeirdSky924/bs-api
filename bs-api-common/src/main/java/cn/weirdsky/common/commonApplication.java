package cn.weirdsky.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.weirdsky.common.mapper")
public class commonApplication {

    public static void main(String[] args) {
        SpringApplication.run(commonApplication.class, args);
    }

}

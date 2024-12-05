package cn.weirdsky.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@MapperScan("cn.weirdsky.mapper")
public class statisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(statisticsApplication.class, args);
    }

}

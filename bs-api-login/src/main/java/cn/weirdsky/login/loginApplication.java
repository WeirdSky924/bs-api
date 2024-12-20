package cn.weirdsky.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.weirdsky.mapper")
public class loginApplication {

    public static void main(String[] args) {
        SpringApplication.run(loginApplication.class, args);
    }

}

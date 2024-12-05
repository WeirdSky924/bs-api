package cn.weirdsky.department;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("cn.weirdsky.mapper")
public class departmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(departmentApplication.class, args);
    }


}

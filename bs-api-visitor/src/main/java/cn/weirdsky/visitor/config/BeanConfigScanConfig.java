package cn.weirdsky.visitor.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@ComponentScans(value =
        {@ComponentScan(value = "cn.weirdsky")})
@EntityScan(basePackages = {"cn.weirdsky"})
@Configuration
public class BeanConfigScanConfig implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("##################################初始化 BeanConfigScan ################################################");
    }
}

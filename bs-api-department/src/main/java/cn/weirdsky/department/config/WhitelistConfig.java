package cn.weirdsky.department.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "interceptor.whitelist")
public class WhitelistConfig {

    private List<String> ips;
    private List<String> userAgents;
    private List<String> ports;

}
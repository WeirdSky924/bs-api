package cn.weirdsky.statistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login/getLogin", "/login/reg", "/login/logout", "/user/getInfo", "/**").permitAll() // 无需认证的端点
                .anyRequest().authenticated()                       // 其他请求需要认证
            )
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 无状态会话

        return http.build();
    }
}
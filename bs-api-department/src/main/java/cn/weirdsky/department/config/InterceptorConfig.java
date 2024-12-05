package cn.weirdsky.department.config;

import cn.weirdsky.department.interceptor.CommonInterceptor;
import cn.weirdsky.department.interceptor.SysLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private SysLogInterceptor sysLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/visitor_log/in", "/visitor_log/in2", "/visitor_log/out", "/visitor_log/out2", "/swagger-ui/**");
        registry.addInterceptor(sysLogInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**");
    }
}

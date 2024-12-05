package cn.weirdsky.department.config;

import io.sentry.spring.jakarta.EnableSentry;
import org.springframework.context.annotation.Configuration;

@EnableSentry(dsn = "https://ebeb8f50c2aba4f0649a2c9f20ccd288@o4508320075743232.ingest.us.sentry.io/4508320170115072")
@Configuration
class SentryConfiguration {

}
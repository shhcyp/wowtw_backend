package cn.wowtw_backend.config;

import cn.wowtw_backend.filter.VisitCounterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final VisitCounterInterceptor visitCounterInterceptor;

    @Autowired
    public WebConfig(VisitCounterInterceptor visitCounterInterceptor) {
        this.visitCounterInterceptor = visitCounterInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitCounterInterceptor).addPathPatterns("/api/hello");
    }
}

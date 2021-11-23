package com.zbl.springboot.config;

import com.zbl.springboot.interceptor.RequestCountRecordInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/23 15:24
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @SuppressWarnings("all")
    @Autowired
    private RequestCountRecordInterceptor requestCountRecordInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestCountRecordInterceptor)
                .addPathPatterns("/**") //添加拦截路径
                .excludePathPatterns("/static/**", "/css/**"); //添加放行路径
    }
}

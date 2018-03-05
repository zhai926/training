package com.lehui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器
 * Created by SunHaiyang on 2017/6/7.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/upload",
                "/uploadOne",
                "/brand/findAllByShow",
                "/floor/find",
                "/news/findList",
                "/news/find",
                "/member/login",
                "/member/logout",
                "/newsClass/findAll"
        );
        super.addInterceptors(registry);
    }
}

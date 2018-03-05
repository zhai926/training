package com.lehui;

import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by SunHaiyang on 2017/6/29.
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TrainingApplication.class);
    }

}

package com.lehui.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 上传配置
 * Created by SunHaiyang on 2017/6/12.
 */
@Configuration
public class UploadConfig {

    /**
     * 上传地址
     */
    @Value("${server.uploadPath}")
    public String UPLOAD_PATH;

    /**
     * 链接地址
     */
    @Value("${server.filebase}")
    public String FILE_BASE;
}

package com.lehui.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件逻辑类
 * Created by SunHaiyang on 2017/6/12.
 */
public interface UploadService {

    /**
     * 上传多个文件
     * @return
     */
    public List<String> upload(MultipartFile[] multipartFiles);

    /**
     * 上传单个文件
     * @return
     */
    public String upload(MultipartFile multipartFiles);
}

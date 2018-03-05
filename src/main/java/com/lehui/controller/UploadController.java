package com.lehui.controller;

import java.util.List;

import com.lehui.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传图片
 * Created by SunHaiyang on 2017/6/12.
 */
@RestController
@CrossOrigin
public class UploadController {
    @Autowired
    UploadService uploadService;

    /**
     * 批量删除
     * @param multipartFiles file图片
     * @return 图片路径
     */
    @RequestMapping(value = "/upload")
    public List<String> uploadImg(@RequestParam(name = "file") MultipartFile[] multipartFiles, HttpServletRequest httpServletRequest){
        return uploadService.upload(multipartFiles);
    }

    /**
     * 删除
     * @param multipartFile file图片
     * @return 图片路径
     */
    @RequestMapping(value = "/uploadOne")
    public String uploadImg(@RequestParam(name = "file") MultipartFile multipartFile){
        return uploadService.upload(multipartFile);
    }

}

package com.lehui.service.impl;

import com.lehui.config.UploadConfig;
import com.lehui.service.UploadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 图片上传
 * Created by SunHaiyang on 2017/6/12.
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    UploadConfig uploadConfig;

    SimpleDateFormat folder  = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Override
    public List<String> upload(MultipartFile[] multipartFiles) {
        List<String> list = new ArrayList<String>();
        for (MultipartFile multipartFile:multipartFiles
                ) {
            list.add(upload(multipartFile));
        }
        return list;
    }

    @Override
    public String upload(MultipartFile multipartFiles) {
        String path = null;
        Date date = new Date();
        String dirPath = folder.format(date);
        String imgName = null;
        if (!multipartFiles.isEmpty()) {
            try {
                long fileSize = multipartFiles.getSize(); // 获取图片大小
                String imageType = isImage(multipartFiles.getInputStream()); //获取图片真实类型
                if (imageType == null) {  //未获取到图片真实类型(基本可以确定文件不是图片)
                    return null;
                } else {
                    createDirectory(uploadConfig.UPLOAD_PATH + "\\" + dirPath);
                    imgName = sdf.format(date) + "." + imageType;
                    File file = new File(uploadConfig.UPLOAD_PATH + "\\" + dirPath + "\\" + imgName);
                    if (!file.exists())
                        file.createNewFile();
                    FileUtils.writeByteArrayToFile(file, multipartFiles.getBytes());
                    path = uploadConfig.FILE_BASE + dirPath + "/" + imgName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    /**
     * 判断路径是否创建
     * @param path
     * @return
     */
    public boolean createDirectory(String path) {
        boolean falg = true;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }else{
            if(!file.isDirectory()){
                file.mkdirs();
            }
        }
        return falg;
    }

    /**
     * 验证是否是图片
     * @param inputStream
     * @return
     */
    public String isImage(InputStream inputStream){
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
            Iterator<ImageReader> imgRead = ImageIO.getImageReaders(iis);
            if(!imgRead.hasNext()){
                return null;
            }
            ImageReader imageReader = imgRead.next();
            iis.close();
            return imageReader.getFormatName();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

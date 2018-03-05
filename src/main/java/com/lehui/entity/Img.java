package com.lehui.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 图片实体类
 * Created by SunHaiyang on 2017/6/6.
 */
@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Img implements Serializable{

    /**
     * 标题
     */
    @NonNull
    private String name;

    /**
     * 地址
     */
    @NonNull
    private String path;

    /**
     * 链接
     */
    private String url;
}

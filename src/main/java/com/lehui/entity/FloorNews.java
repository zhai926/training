package com.lehui.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 文章缩略实体
 * Created by SunHaiyang on 2017/6/12.
 */
@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class FloorNews implements Serializable {
    /**
     * 文章id
     */
    @NonNull
    private String id;

    /**
     * 文章标题
     */
    @NonNull
    private String title;

    /**
     * 图片路径
     */
    @NonNull
    private String path;

    /**
     * 简单内容
     */
    @NonNull
    private String content;

    /**
     * 是否大图片展示
     */
    @NonNull
    private boolean bigPic;
}

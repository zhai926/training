package com.lehui.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * brand图
 * Created by SunHaiyang on 2017/6/9.
 */
@Data
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Brand implements Serializable {

    /**
     * brand图 id
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * brand图 标题
     */
    @NonNull
    private String title;

    /**
     * brand图 图片地址
     */
    @NonNull
    private String path;

    /**
     * brand图 链接
     */
    @NonNull
    private String url;

    /**
     * brand图 排序
     */
    @NonNull
    private int sort;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 一直显示
     */
    private boolean onlyShow;





}

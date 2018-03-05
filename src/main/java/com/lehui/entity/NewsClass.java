package com.lehui.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 新闻分类
 * Created by SunHaiyang on 2017/6/6.
 */
@Entity
@Data
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class NewsClass implements Serializable {

    /**
     * 分类ID
     */
    @Id
    @GenericGenerator(name = "sys-uid",strategy = "com.lehui.utils.KeyGeneratorUtils",parameters = {
            @Parameter(name = "k",value = "C")
    })
    @GeneratedValue(generator = "sys-uid")
    private String id;

    /**
     * 分类名称
     */
    @NonNull
    private String name;

    /**
     * 分类排序
     */
    @NonNull
    private int sort = 0;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    @JsonBackReference
    private boolean isDel = Boolean.FALSE;

}


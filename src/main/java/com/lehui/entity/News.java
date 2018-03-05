package com.lehui.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 新闻
 * Created by SunHaiyang on 2017/6/6.
 */
@Entity
@Data
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class News implements Serializable {

    /**
     * 新闻ID
     */
    @Id
    @GenericGenerator(name = "sys-uid",strategy = "com.lehui.utils.KeyGeneratorUtils",parameters = {
            @Parameter(name = "k",value = "N")
    })
    @GeneratedValue(generator = "sys-uid")
    private String id;

    /**
     * 新闻标题
     */
    @NonNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    /**
     * 新闻内容
     */
    @NonNull
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StringBuffer content;

    /**
     * 新闻图片
     */
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Img> img;

    @JsonBackReference
    @Lob
    private String imgs;


    /**
     * 发布时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();
    /**
     * 更新时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除
     */
    @JsonBackReference
    private boolean isDel = Boolean.FALSE;

    /**
     * 发布用户
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 新闻分类
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "class_id")
    private NewsClass newsClass;



    @PostLoad
    private void load(){
        if(imgs != null){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Img>>() {}.getType();
            img = gson.fromJson(imgs,type);
            imgs = null;
        }
    }

    @PrePersist
    @PreUpdate
    private void save(){
        if(img != null){
            Gson gson = new Gson();
            imgs = gson.toJson(img);
        }

    }


    public News(String id,String title, Date createTime) {
        this.id = id;
        this.title = title;
        this.createTime = createTime;
    }
}

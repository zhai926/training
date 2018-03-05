package com.lehui.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lehui.utils.Constants;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 新闻展示model
 * Created by SunHaiyang on 2017/6/9.
 */
@Entity
@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class FloorModel implements Constants,Serializable {

    /**
     * id
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 展示格式
     */
    private int showFormat = SHOW_NEWS_ONE;

    /**
     * 创建对象
     */

    @NonNull
    @Transient
    private List<FloorNews> floorNewsList;

    /**
     * 存储的对象
     */
    @JsonBackReference
    @Lob
    private String newsList;

    /**
     * 排序
     */
    @NonNull
    private int sort;

    /**
     * 读取数据库时进行JSON from Entity的转换
     */
    @PostLoad //读取
    private void load(){
        if(newsList != null){
            Type type = new TypeToken<ArrayList<FloorNews>>(){}.getType();
            Gson gson = new Gson();
            floorNewsList =  gson.fromJson(newsList,type);
            newsList = null;
        }

    }


    /**
     * 存储或更新的时候进行Entity from JSON 的转换
     */
    @PrePersist
    @PreUpdate
    private void save(){
        if(floorNewsList != null){
            Gson gson = new Gson();
            newsList = gson.toJson(floorNewsList);
        }

    }

}

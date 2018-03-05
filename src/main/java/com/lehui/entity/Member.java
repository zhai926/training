package com.lehui.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 * Created by SunHaiyang on 2017/6/6.
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Member implements Serializable {

    /**
     * 用户ID
     */
    @Id
    @GenericGenerator(name = "sys-uid",strategy = "com.lehui.utils.KeyGeneratorUtils",parameters = {
            @Parameter(name = "k",value = "U")
    })
    @GeneratedValue(generator = "sys-uid")
    private String id;

    /**
     * 用户名称
     */
    @NonNull
    private String name;

    /**
     * 用户名
     */
    @NonNull
    private String username;

    /**
     * 密码
     */
    @NonNull
    @JsonBackReference
    private String password;

    /**
     * 登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 是否删除
     */
    @JsonBackReference
    private boolean isDel = Boolean.FALSE;


}

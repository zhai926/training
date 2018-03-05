package com.lehui.service;

import com.lehui.entity.NewsClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * newsClass逻辑类
 * Created by SunHaiyang on 2017/6/7.
 */
public interface NewsClassService {

    /**
     * 存储newsClass
     * @param newsClass
     * @return
     */
    public NewsClass saveNewsClass(NewsClass newsClass);

    /**
     * 更新newsClass
     * @param newsClass
     * @return
     */
    public NewsClass updateNewsClass(NewsClass newsClass);

    /**
     * 根据id删除newsClass
     * @param id
     * @return
     */
    public boolean deleteNewsClass(String id);

    /**
     * 根据id查询newsClass
     * @param id
     * @return
     */
    public NewsClass findById(String id);

    /**
     * 根据name查询newsClass
     * @param name
     * @return
     */
    public List<NewsClass> findByName(String name);

    /**
     * 分页查询newsClass
     * @param pageable
     * @return
     */
    public Page<NewsClass> findAll(Pageable pageable);

}

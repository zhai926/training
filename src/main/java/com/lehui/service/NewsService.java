package com.lehui.service;

import com.lehui.entity.Member;
import com.lehui.entity.NewsClass;
import com.lehui.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;


/**
 * news逻辑类
 * Created by SunHaiyang on 2017/6/7.
 */
public interface NewsService {

    /**
     * 保存news
     * @param news
     * @return
     */
    public News saveNews(News news);

    /**
     * 更新news
     * @param news
     * @return
     */
    public News updateNews(News news);

    /**
     * 根据newsClass及分页查询news
     * @param pageable
     * @param newsClass
     * @return
     */
    public Page<News> findByNewsClass(Pageable pageable, NewsClass newsClass);

    /**
     * 根据id删除news
     * @param id
     * @return
     */
    public boolean deleteNewsById(String id);

    /**
     * 通过条件查询news
     * @param pageable
     * @return
     */
    public Page<News> findAll(
            String title,
            List<Member> members,
            NewsClass newsClass,
            Date startDate,
            Date endDate,
            Pageable pageable
    );

    /**
     * 根据id查询news
     * @param id
     * @return
     */
    public News findById(String id);

    /**
     * 根据 NewsClass查询简单的news
     * @param newsClass
     * @param pageable
     * @return
     */
    public Page<News> findSimpleNewsByNewsClass(NewsClass newsClass,Pageable pageable);


}

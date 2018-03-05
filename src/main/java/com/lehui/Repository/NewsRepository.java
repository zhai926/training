package com.lehui.Repository;

import com.lehui.entity.NewsClass;
import com.lehui.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 新闻持久层
 * Created by SunHaiyang on 2017/6/6.
 */
public interface NewsRepository extends JpaRepository<News,String>,JpaSpecificationExecutor<News> {

    /**
     * 通过newClass及分页插件查询news
     * @param pageable
     * @param newsClass
     * @return
     */
    public Page<News> findByNewsClassAndIsDelFalseOrderByCreateTimeDesc(Pageable pageable, NewsClass newsClass);


    @Query(value = "select new News(n.id,n.title,n.createTime) from News n where n.newsClass = ?1 and n.isDel = FALSE order by n.createTime desc")
    public Page<News> findNewsByNewsClass(NewsClass newsClass,Pageable pageable);

    /**
     * 删除news
     * @param id
     */
    @Modifying
    @Query(value = "update News n set n.isDel = true where n.id = ?1")
    public void deleteById(String id);



    /**
     * 分页查询news
     * @param pageable
     * @return
     */
    public Page<News> findAllByIsDelFalseOrderByCreateTimeDesc(Specification<News> newsSpecification, Pageable pageable);



    public News findByIdAndIsDelFalseOrderByCreateTimeDesc(String id);

}

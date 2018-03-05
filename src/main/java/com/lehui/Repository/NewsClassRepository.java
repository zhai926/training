package com.lehui.Repository;

import com.lehui.entity.NewsClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 新闻分类持久层
 * Created by SunHaiyang on 2017/6/6.
 */
@Repository
public interface NewsClassRepository extends JpaRepository<NewsClass,String> {

    /**
     * 查询所有newClass通过Sort排序
     * @param pageable
     * @return
     */
    public Page<NewsClass> findAllByIsDelFalseOrderBySort(Pageable pageable);

    /**
     * 逻辑删除newClass
     * @param id
     */
    @Modifying
    @Query(value = "update NewsClass n set n.isDel = true where n.id = ?1")
    public void deleteById(String id);

    /**
     * 通过名称查询newClass
     * @param name
     * @return
     */
    public List<NewsClass> findByNameContainingAndIsDelFalse(String name);

    /**
     * 通过id查询newClass
     * @param id
     * @return
     */
    public NewsClass findByIdAndIsDelFalse(String id);




}

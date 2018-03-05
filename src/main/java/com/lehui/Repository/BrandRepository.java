package com.lehui.Repository;

import com.lehui.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 删除brand Image图片
 * Created by SunHaiyang on 2017/6/12.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> , JpaSpecificationExecutor<Brand> {


    /**
     * 查询所有
     * @return
     */
    public List<Brand> findByOrderBySort();



}

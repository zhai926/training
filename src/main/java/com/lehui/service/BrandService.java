package com.lehui.service;


import com.lehui.entity.Brand;
import java.util.List;

/**
 * brand 逻辑层
 * Created by SunHaiyang on 2017/6/12.
 */
public interface BrandService {

    /**
     * 查询当前展示的brand
     * @return
     */
    public List<Brand> findAllByShow();

    /**
     * 查询所有brand
     * @return
     */
    public List<Brand> findAll();

    /**
     * 保存brand
     * @param brand
     * @return
     */
    public Brand save(Brand brand);

    /**
     * 删除brand
     * @param id
     * @return
     */
    public boolean deleteBrandById(long id);

    /**
     * 更新brand
     * @param brand
     * @return
     */
    public Brand updateBrand(Brand brand);

    /**
     * 通过id查询brand
     * @param id
     * @return
     */
    public Brand findbyId(long id);
}

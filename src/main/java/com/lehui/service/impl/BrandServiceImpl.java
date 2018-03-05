package com.lehui.service.impl;

import com.lehui.Repository.BrandRepository;
import com.lehui.entity.Brand;
import com.lehui.service.BrandService;
import com.lehui.utils.SpecsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by SunHaiyang on 2017/6/12.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> findAllByShow() {
        final Date date = new Date();
        List<Brand> brands = brandRepository.findAll();
        List<Brand> brandList = new ArrayList<Brand>();
        for (Brand brand : brands){
            if (brand.getStartDate().before(date) && brand.getEndDate().after(date) || brand.isOnlyShow()){
                brandList.add(brand);
            }
        }
        return brandList;
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findByOrderBySort();
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public boolean deleteBrandById(long id) {
        boolean flag = true;
        try {
            brandRepository.delete(id);
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand findbyId(long id) {
        return brandRepository.findOne(id);
    }
}

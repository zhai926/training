package com.lehui.controller;

import com.lehui.entity.Brand;
import com.lehui.service.BrandService;
import com.lehui.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * brand 图 controller
 * Created by SunHaiyang on 2017/6/12.
 */
@RestController
@RequestMapping(value = "/brand",method = RequestMethod.POST)
@CrossOrigin
public class BrandController implements Constants {

    @Autowired
    BrandService brandService;

    /**
     * 存储brand
     * @param title 标题
     * @param path 路径
     * @param url 链接
     * @param sort 排序
     * @param onlyShow 是否一直显示 (可空,默认true)
     * @param startDate 开始展示时间
     * @param endDate 结束展示时间
     * @return {state:'success'|'error',brand:brand}
     */
    @RequestMapping(value = "/save")
    public Map<String,Object> saveBrand(
            @RequestParam(name = "title")String title,
            @RequestParam(name = "path")String path,
            @RequestParam(name = "url")String url,
            @RequestParam(name = "sort",required = false,defaultValue = "0")Integer sort,
            @RequestParam(name = "onlyShow",required = false,defaultValue = "true") Boolean onlyShow,
            @RequestParam(name = "startDate",required = false,defaultValue = "")Long startDate,
            @RequestParam(name = "endDate",required = false,defaultValue = "")Long endDate
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Brand brand = new Brand(title,path,url,sort);
        if (onlyShow){
            brand.setOnlyShow(onlyShow);
            brand.setStartDate(new Date());
            brand.setEndDate(new Date());
        }else{
            brand.setStartDate(new Date(startDate));
            brand.setEndDate(new Date(endDate));
        }
        brand = brandService.save(brand);
        if(brand != null){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("brand",brand);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 更新brand
     * @param id brand id
     * @param title 标题
     * @param path 路径
     * @param url 链接
     * @param sort 排序
     * @param onlyShow 是否一直显示 (可空,默认true)
     * @param startDate 开始展示时间
     * @param endDate 结束展示时间
     * @return {state:'success'|'error',brand:brand}
     */
    @RequestMapping(value = "/update")
    public Map<String,Object> updateBrand(
            @RequestParam(name = "id")Long id,
            @RequestParam(name = "title")String title,
            @RequestParam(name = "path")String path,
            @RequestParam(name = "url")String url,
            @RequestParam(name = "sort",required = false,defaultValue = "0")Integer sort,
            @RequestParam(name = "onlyShow",required = false,defaultValue = "true") Boolean onlyShow,
            @RequestParam(name = "startDate",required = false,defaultValue = "")Long startDate,
            @RequestParam(name = "endDate",required = false,defaultValue = "")Long endDate
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Brand brand = brandService.findbyId(id);
        brand.setTitle(title);
        brand.setPath(path);
        brand.setUrl(url);
        brand.setSort(sort);
        if(onlyShow){
            brand.setOnlyShow(onlyShow);
            brand.setStartDate(new Date());
            brand.setEndDate(new Date());
        }else{
            brand.setOnlyShow(onlyShow);
            brand.setStartDate(new Date(startDate));
            brand.setEndDate(new Date(endDate));
        }
        brand = brandService.save(brand);
        if(brand != null){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("brand",brand);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     *
     * 删除brand
     * @param id brand id
     * @return {state:'success'|'error'}
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteBrand(
            @RequestParam(name = "id")Long id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        if(brandService.deleteBrandById(id)){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 查找显示Brand
     * @return {state:'success'|'error',brands:brands}
     */
    @RequestMapping(value = "/findAllByShow")
    public Map<String,Object> findAllByShow(){
        Map<String,Object> map = new HashMap<String,Object>();
        List<Brand> brands = brandService.findAllByShow();
        if(brands.size() > 0){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("brands",brands);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 查找所有Brand
     * @return {state:'success'|'error',brands:brands}
     */
    @RequestMapping(value = "/findAll")
    public Map<String,Object> findAll(){
        Map<String,Object> map = new HashMap<String,Object>();
        List<Brand> brands = brandService.findAll();
        if(brands.size() > 0){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("brands",brands);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 通过id 查询brand
     * @param id brand id
     * @return {state:'success'|'error',brand:brand}
     */
    @RequestMapping(value = "/find")
    public Map<String,Object> find(
            @RequestParam(name = "id")Long id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Brand brand = brandService.findbyId(id);
        if(brand != null){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("brand",brand);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }
}

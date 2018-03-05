package com.lehui.controller;

import com.lehui.entity.NewsClass;
import com.lehui.service.NewsClassService;
import com.lehui.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 新闻分类 Controller
 * Created by SunHaiyang on 2017/6/7.
 */
@RestController
@RequestMapping(value = "/newsClass",method = RequestMethod.POST)
@CrossOrigin
public class NewClassController implements Constants {

    @Autowired
    NewsClassService newsClassService;

    /**
     * 保存newsClass
     * @param name 分类名称
     * @param sort 排序 (可空)
     * @return {state:'success'|'error',newsClass:newsClass}
     */
    @RequestMapping(value = "/save")
    public Map<String,Object> saveNewClass(
            @RequestParam(name = "name")String name,
            @RequestParam(name = "sort",required = false,defaultValue = "0")int sort
            ){
        Map<String,Object> map = new HashMap<String,Object>();
        NewsClass newsClass = new NewsClass(name);
        newsClass.setCreateTime(new Date());
        newsClass.setSort(sort);
        try {
            newsClass = newsClassService.saveNewsClass(newsClass);
            if(newsClass!= null){
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put("newsClass",newsClass);
            }else{
                map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            }
        }catch (Exception e){
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 删除newsClass
     * @param id newsClass id
     * @return {state:'success'|'error'}
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteNewsClass(
            @RequestParam(name = "id")String id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        if(newsClassService.deleteNewsClass(id)){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 更新newsClass
     * @param id newsClass id
     * @param name newsClass 名称 (可空)
     * @param sort newsClass 排序 (可空)
     * @return {state:'success'|'error',newsClass:newsClass}
     */
    @RequestMapping("/update")
    public Map<String,Object> updateNewsClass(
            @RequestParam(name = "id")String id,
            @RequestParam(name = "name",required = false,defaultValue = "")String name,
            @RequestParam(name = "sort",required = false,defaultValue = "-1")int sort
    ){
        Map<String,Object> map = new HashMap<>();
        NewsClass newsClass = newsClassService.findById(id);
        if(name != null && name.length()>0){
            newsClass.setName(name);
        }
        if(sort > -1){
            newsClass.setSort(sort);
        }
        try {
            newsClass = newsClassService.updateNewsClass(newsClass);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("newsClass",newsClass);
        }catch (Exception e){
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询分类
     * @param page 页面 (可空)
     * @param pageSize 页面大小 (可空)
     * @return {state:'success'|'error',newsClass:newsClass}
     */
    @RequestMapping(value = "/findAll")
    public Map<String,Object> findAll(
            @RequestParam(name = "page",required = false ,defaultValue = "0")int page,
            @RequestParam(name = "pageSize",required = false,defaultValue = "15")int pageSize
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Sort sort = new Sort(Sort.Direction.ASC,"sort");
        Pageable pageable = new PageRequest(page,pageSize,sort);
        Page<NewsClass> newsClasses = newsClassService.findAll(pageable);
        if(newsClasses.getContent().size() > 0){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("newsCalss",newsClasses);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }


    /**
     * 查询newsClass
     * @param name newsClass名称
     * @param id newsClass id
     * @return {state:'success'|'error',newsClass:newsClass}
     */
    @RequestMapping(value = "/find")
    public Map<String,Object> findNewsClass(
            @RequestParam(name = "name",required = true,defaultValue = "")String name,
            @RequestParam(name = "id",required = true,defaultValue = "")String id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        List<NewsClass> newsClassList = new ArrayList<NewsClass>();
        if(name != null && name.length() > 0){
            newsClassList = newsClassService.findByName(name);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("newsClass",newsClassList);
        }else if(id != null && id.length() > 0){
            NewsClass newsClass = newsClassService.findById(id);
            newsClassList.add(newsClass);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("newsClass",newsClassList);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }


}

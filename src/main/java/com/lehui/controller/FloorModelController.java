package com.lehui.controller;

import com.lehui.entity.FloorModel;
import com.lehui.entity.FloorNews;
import com.lehui.entity.News;
import com.lehui.entity.NewsClass;
import com.lehui.service.FloorModelService;
import com.lehui.service.NewsService;
import com.lehui.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 楼层 controller
 * Created by SunHaiyang on 2017/6/12.
 */
@RestController
@RequestMapping(value = "/floor")
@CrossOrigin
public class FloorModelController implements Constants {

    @Autowired
    NewsService newsService;

    @Autowired
    FloorModelService floorService;

    /**
     * 清除html标签
     * @param content
     * @return
     */
    public String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }

    /**
     * 添加展示文章
     * @param newsIds 文章id 数组 id,id,id
     * @param bigPic 是否显示大图 bigPic,bigPic,bigPic
     * @param sort 第几行
     * @return {state:'success'|'error',floorModel,floorModel}
     */
    @RequestMapping(value = "/save")
    public Map<String,Object> saveFloor(
            @RequestParam(name = "newsIds")String[] newsIds,
            @RequestParam(name = "bigPic",required = false,defaultValue = "false")boolean[] bigPic,
            @RequestParam(name = "format",required = false,defaultValue = "0")int showFormat,
            @RequestParam(name = "sort",required = false,defaultValue = "0")int sort
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        List<FloorNews> floorNewsList = new ArrayList<FloorNews>();
        int i = 0;
        for (String id:newsIds
             ) {
            News news = newsService.findById(id);
            if(news != null){
                int index = 0;
                String str = news.getContent().toString();
                String[] regex = {"/n","</p>","<br>","<br/>"};
                int u = 0;
                while (index <= 0 && regex.length < u){
                    str.indexOf(regex[u]);
                    u ++;
                }
                String content = null;
                if(index == 0){
                    content = str;
                }else{
                    content = str.substring(0,index);

                }
                content = stripHtml(content);
                FloorNews floorNews = new FloorNews(news.getId(),news.getTitle(),news.getImg().get(0).getPath(),content,bigPic[i]);
                floorNewsList.add(floorNews);
                i += 1;
            }
        }
        FloorModel floorModel = new FloorModel(floorNewsList,sort);
        if(showFormat > 0){
            floorModel.setShowFormat(showFormat);
        }else{
            floorModel.setShowFormat(SHOW_NEWS_ONE);
        }
        floorModel.setCreateTime(new Date());
        floorModel = floorService.saveFloorModel(floorModel);
        if(floorModel != null){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("floorModel",floorModel);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 更新floor model
     * @param id floor model id
     * @param newsIds 文章id
     * @param bigPic 是否大图片显示
     * @param sort 排序
     * @return {state:'success'|'error',floorModel,floorModel}
     */
    @RequestMapping(value = "/update")
    public Map<String,Object> updateFloor(
            @RequestParam(value = "id")Long id,
            @RequestParam(value = "newsIds",required = false)String[] newsIds,
            @RequestParam(name = "format",required = false,defaultValue = "0")int showFormat,
            @RequestParam(value = "bigPic",required = false,defaultValue = "false")boolean[] bigPic,
            @RequestParam(value = "sort",required = false,defaultValue = "0")int sort
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        FloorModel floorModel = floorService.findById(id);
        int i = 0;
        if(newsIds.length > 0){
            List<FloorNews> floorNewsList = new ArrayList<FloorNews>();
            for (String newsId:newsIds
                    ) {
                News news = newsService.findById(newsId);
                if(news != null){
                    int index = 0;
                    String str = news.getContent().toString();
                    String[] regex = {"/n","</p>","<br>","<br/>"};
                    int u = 0;
                    while (index <= 0 && regex.length < u){
                        str.indexOf(regex[u]);
                        u ++;
                    }
                    String content = null;
                    if(index == 0){
                        content = str;
                    }else{
                        content = str.substring(0,index);

                    }
                    content = stripHtml(content);
                    FloorNews floorNews = new FloorNews(news.getId(),news.getTitle(),news.getImg().get(0).getPath(),content,bigPic[i]);
                    floorNewsList.add(floorNews);
                    i += 1;
                }
            }
            floorModel.setFloorNewsList(floorNewsList);
            floorModel.setNewsList(null);
        }
        if(sort > 0){
            floorModel.setSort(sort);
        }
        if(showFormat > 0){
            floorModel.setShowFormat(showFormat);
        }
        floorModel = floorService.updateFloorModel(floorModel);
        if(floorModel != null){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("floorModel",floorModel);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 根据id删除floorModel
     * @param id model id
     * @return {state:'success'|'error'}
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteFloorModel(@RequestParam(name = "id")Long id){
        Map<String,Object> map = new HashMap<String,Object>();
        if(floorService.deleteFloorModel(id)){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 查询floor Model
     * @param id floor model id
     * @return {state:'success'|'error',floorModel|floorModels:floorModel|floorModels}
     */
    @RequestMapping(value = "/find")
    public Map<String,Object> findById(
            @RequestParam(name = "id",required = false,defaultValue = "0")Long id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        if (id > 0){
            FloorModel floorModel = floorService.findById(id);
            if(floorModel != null){
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put("floorModel",floorModel);
            }else{
                map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            }
        }else{
            List<FloorModel> floorModels = floorService.findAll();
            if(floorModels.size() > 0){
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put("floorModels",floorModels);
            }else{
                map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            }
        }
        return map;
    }

}

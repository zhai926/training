package com.lehui.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lehui.entity.Img;
import com.lehui.entity.Member;
import com.lehui.entity.News;
import com.lehui.entity.NewsClass;
import com.lehui.service.MemberService;
import com.lehui.service.NewsClassService;
import com.lehui.service.NewsService;
import com.lehui.utils.Constants;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 新闻 Controller
 * Created by SunHaiyang on 2017/6/8.
 */
@RestController
@RequestMapping(value = "/news",method = RequestMethod.POST)
@Log4j
@CrossOrigin
public class NewsController implements Constants {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsClassService newsClassService;

    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/link")
    public void link(){
        log.info("Session 续期成功...");
    }

    /**
     * 发布文章
     * @param title 标题
     * @param content 内容
     * @param classId 分类ID
     * @param imgs 图片JSON
     * @return {state:'success'|'error',news:news}
     */
    @RequestMapping(value = "/save")
    public Map<String,Object> saveNews(
            @RequestParam(name = "title")String title,
            @RequestParam(name = "content")StringBuffer content,
            @RequestParam(name = "classId")String classId,
            @RequestParam(name = "imgs") String imgs,
            HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            News news = new News();
            Member member = (Member)request.getSession().getAttribute(MEMBER_INFO);
            NewsClass newsClass = newsClassService.findById(classId);
            news.setCreateTime(new Date());
            news.setTitle(title);
            news.setContent(content);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Img>>(){}.getType();
            try {
                List<Img> img = gson.fromJson(imgs,type);
                news.setImg(img);
            }catch (Exception e){
                map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
                return map;
            }
            news.setMember(member);
            news.setNewsClass(newsClass);
            news = newsService.saveNews(news);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("news",news);
        }catch (Exception e){
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 更新文章
     * @param id 文章ID
     * @param title 文章标题
     * @param content 文章内容
     * @param classId 分类ID
     * @param imgs 图片地址
     * @return {state:'success'|'error',news:news}
     */
    @RequestMapping(value = "/update")
    public Map<String,Object> updateNews(
            @RequestParam(name = "id")String id,
            @RequestParam(name = "title")String title,
            @RequestParam(name = "content")StringBuffer content,
            @RequestParam(name = "classId")String classId,
            @RequestParam(name = "imgs")String imgs
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        News news = newsService.findById(id);
        NewsClass newsClass = newsClassService.findById(classId);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Img>>() {}.getType();
        List<Img> imgList = gson.fromJson(imgs,type);
        news.setImg(imgList);
        news.setTitle(title);
        news.setUpdateTime(new Date());
        news.setContent(content);
        news.setNewsClass(newsClass);
        try {
            newsService.updateNews(news);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("news",news);
        }catch (Exception e){
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 删除新闻
     * @param id 新闻id
     * @return {state:'success'|'error'}
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteNews(
            @RequestParam(name = "id")String id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        boolean flag = newsService.deleteNewsById(id);
        if(flag){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }


    /**
     * 根据条件查询news (全空则代表查询全部)
     * @param id 新闻id
     * @param classId 分类id (可空)
     * @param title 标题 (可空)
     * @param name 用户名称 (可空)
     * @param username 账号 (可空)
     * @param memberId 用户id (可空)
     * @param startDate 开始时间 (可空)
     * @param endDate 结束时间 (可空)
     * @param page 页码 (可空,默认0)
     * @param pageSize 页面数量 (可空,默认15)
     * @return {state:'success'|'error',news:news}
     */
    @RequestMapping(value = "/find")
    public Map<String,Object> findNews(
            @RequestParam(name = "id",required = false ,defaultValue = "")String id,
            @RequestParam(name = "classId",required = false,defaultValue = "")String classId,
            @RequestParam(name = "title",required = false,defaultValue = "")String title,
            @RequestParam(name = "name",required = false,defaultValue = "")String name,
            @RequestParam(name = "username",required = false,defaultValue = "")String username,
            @RequestParam(name = "memberId",required = false,defaultValue = "")String memberId,
            @RequestParam(name = "startDate",required = false,defaultValue = "")Long startDate,
            @RequestParam(name = "endDate",required = false,defaultValue = "")Long endDate,
            @RequestParam(name = "page",required = false,defaultValue = "0")int page,
            @RequestParam(name = "pageSize",required = false,defaultValue = "20")int pageSize
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(page,pageSize,sort);
        Date startTime = null;
        Date endTime = null;
        List<Member> members = null;
        NewsClass newsClass = null;
        if(id != null && id.length() > 0){
            News news = newsService.findById(id);
            if(news != null){
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put("news",news);
                return map;
            }
        }
        if(startDate != null && startDate > 0){
            startTime = new Date(startDate);
        }
        if(endDate != null && endDate > 0){
            endTime = new Date(endDate);
        }
        if(memberId != null && !memberId.equals("")){
            Member member = memberService.findMemberById(memberId);
            if(member != member){
                members.add(member);
            }
        }
        if(name != null && !name.equals("")){
            members = memberService.findMemberByeName(name);
        }
        if (username != null && !username.equals("")){
            members = memberService.findMemberByUsernameLike(username);
        }
        if (classId != null && !classId.equals("")){
            newsClass = newsClassService.findById(classId);
        }
        Page<News> newsPage = newsService.findAll(title,members,newsClass,startTime,endTime,pageable);
        if (newsPage != null && newsPage.getContent().size()>0){
            map.put("news",newsPage);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 获取News 简单列表
     * @param classId 类ID
     * @param page 页码 (可空)
     * @param pageSize 页面大小 (可空)
     * @return {state:'success'|'error',news:news}
     */
    @RequestMapping(value = "/findList")
    public Map<String,Object> findNewsClass(
            @RequestParam(name = "classId")String classId,
            @RequestParam(name = "page",required = false,defaultValue = "0")int page,
            @RequestParam(name = "pageSize",required = false,defaultValue = "20")int pageSize
    ){
        Map<String,Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page,pageSize);
        NewsClass newsClass = newsClassService.findById(classId);
        if(newsClass != null){
            Page<News> newsPage = newsService.findSimpleNewsByNewsClass(newsClass,pageable);
            if(newsPage .getContent().size()>0){
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put("news",newsPage);
                return map;
            }
        }
        map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        return map;
    }

}

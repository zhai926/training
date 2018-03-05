package com.lehui.controller;

import com.lehui.entity.Member;
import com.lehui.service.MemberService;
import com.lehui.utils.Constants;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 账号 Controller
 * Created by SunHaiyang on 2017/6/6.
 */
@RestController
@RequestMapping(value = "/member",method = RequestMethod.POST)
@Log4j
@CrossOrigin
public class MemberController implements Constants {

    @Autowired
    MemberService memberService;

    /**
     * 注册账号
     * @param name 名称
     * @param username 账号
     * @param password 密码
     * @return {state:'success'|'error',member:member}
     */
    @RequestMapping(value = "/save")
    public Map<String,Object> registerMember(
            @RequestParam(value = "name")String name,
            @RequestParam(value = "username")String username,
            @RequestParam(value = "password")String password
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Member member = memberService.findMemberByUsername(username);
        if(member == null){
            member = new Member(name,username,password);
            try {
                member = memberService.saveMember(member);
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put("member",member);
            }catch (Exception e){
                map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            }
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 登录账号
     * @param username 账号
     * @param password 密码
     * @return {state:'success'|'error',token:'asdfkasdlkfasjkldfasdf'}
     */
    @RequestMapping(value = "/login")
    public Map<String,Object> loginMember(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "password")String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();
        Object tokenObj = session.getAttribute(SESSION_TOKEN_KEY);
        if(tokenObj == null){
            String token = String.valueOf(tokenObj);
            Map<String,Object>retMap = memberService.verifyMember(username,password);
            if(retMap != null){
                session.setAttribute(SESSION_TOKEN_KEY,retMap.get(SESSION_TOKEN_KEY));
                session.setAttribute(MEMBER_INFO,retMap.get(MEMBER_INFO));
                session.setMaxInactiveInterval(LOGIN_OUT_TIME);
                map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
                map.put(SESSION_TOKEN_KEY,retMap.get(SESSION_TOKEN_KEY));
            }else{
                map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
                map.put("msg","用户名或者密码错误。");
            }
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
            map.put("msg","您已登录，请勿重复登录。");
        }
        return map;
    }

    /**
     * 登出账号
     * @return {state:'success'|'error'}
     */
    @RequestMapping(value = "/logout")
    public Map<String,Object> logoutMember(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        HttpSession session = request.getSession();
        session.invalidate();
        map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
        return map;
    }

    /**
     * 查找账号
     * @param page 页码
     * @param pageSize 显示数量
     * @return {state:'success'|'error',members:members}
     */
    @RequestMapping(value = "/findAll")
    public Map<String,Object> findAll(
            @RequestParam(name = "page")int page,
            @RequestParam(name = "pageSize")int pageSize
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page,pageSize);
        Page<Member> members = memberService.findAll(pageable);
        if (members.getContent().size() > 0){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("members",members);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 删除账号
     * @param id 账号id
     * @return {state:'success'|'error'}
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteMember(
            @RequestParam(name = "id")String id
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Member member = memberService.findMemberById(id);
        if(!member.getUsername().equals("admin")){
            if (memberService.deleteMember(id)) {
                map.put(REQUEST_STATE, REQUEST_STATE_SUCCESS);
                return map;
            }
        }
        map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        return map;
    }

    /**
     * 修改账号信息
     * @param id 账号id
     * @param name 名称 (可空)
     * @param password 密码 (可空)
     * @return {state:'success'|'error',member:member'}
     */
    @RequestMapping("/update")
    public Map<String,Object> updateMember(
            @RequestParam(name = "id")String id,
            @RequestParam(name = "name",required = false,defaultValue = "")String name,
            @RequestParam(name = "password",required = false,defaultValue = "") String password
    ){
        Map<String,Object> map = new HashMap<String,Object>();
        Member member = memberService.findMemberById(id);
        if(name != null && name.length() > 0){
            member.setName(name);
        }
        if (password != null && password.length() > 0){
            member.setPassword(password);
        }
        try {
            member = memberService.updateMember(member);
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("member",member);
        }catch (Exception e){
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

    /**
     * 查询用户
     * @param id 用户id (可空)
     * @param name 用户名称 (可空)
     * @param username 账号 (可空)
     * @return {state:'success'|'error',member:member}
     */
    @RequestMapping(value = "/find")
    public Map<String,Object> findMember(
            @RequestParam(name = "id",required = false,defaultValue = "")String id,
            @RequestParam(name = "name",required = false,defaultValue = "")String name,
            @RequestParam(name = "username",required = false,defaultValue = "")String username,
            @RequestParam(name = "page",required = false,defaultValue = "0")int page,
            @RequestParam(name = "pageSize",required = false,defaultValue = "20")int pageSize
    ){
        Map<String,Object> map = new HashMap<String, Object>();
        Sort sort = new Sort(Sort.Direction.DESC,"loginTime");
        Pageable pageable = new PageRequest(page,pageSize,sort);
        Page<Member>memberPage = memberService.findMember(id,name,username,pageable);
        if(memberPage.getContent().size() > 0){
            map.put(REQUEST_STATE,REQUEST_STATE_SUCCESS);
            map.put("members",memberPage);
        }else{
            map.put(REQUEST_STATE,REQUEST_STATE_ERROR);
        }
        return map;
    }

}

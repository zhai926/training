package com.lehui.service;

import com.lehui.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * member逻辑类
 * Created by SunHaiyang on 2017/6/6.
 */
public interface MemberService {

    /**
     * 保存Member
     * @param member
     * @return
     */
    public Member saveMember(Member member) throws Exception;

    /**
     * 更新Member
     * @param member
     * @return
     */
    public Member updateMember (Member member);

    /**
     * 根据ID删除Member
     * @param id
     * @return
     */
    public boolean deleteMember(String id);

    /**
     * 根据名称查询Member
     * @param name
     * @return
     */
    public List<Member> findMemberByeName(String name);

    /**
     * 根据username模糊查询Member
     * @param username
     * @return
     */
    public List<Member> findMemberByUsernameLike(String username);

    /**
     * 根据用户名查询Member
     * @param username
     * @return
     */
    public Member findMemberByUsername(String username);

    /**
     * 根据ID查询Member
     * @param id
     * @return
     */
    public Member findMemberById(String id);

    /**
     * 验证Member
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> verifyMember(String username, String password);

    /**
     * 获取Member列表
     */
    public Page<Member> findAll(Pageable pageable);

    public Page<Member> findMember(String id, String name, String username,Pageable pageable);


}

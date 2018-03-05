package com.lehui.service.impl;

import com.lehui.Repository.MemberRepository;
import com.lehui.entity.Member;
import com.lehui.service.MemberService;
import com.lehui.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lehui.utils.AESUtils.*;

import static com.lehui.utils.SpecsUtils.*;

import java.util.*;


/**
 * Created by SunHaiyang on 2017/6/6.
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService,Constants {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member saveMember(Member member){
        member.setPassword(encrypt(member.getPassword(),member.getUsername()));
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public boolean deleteMember(String id) {
        try {
            memberRepository.deleteMemberById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Member> findMemberByeName(String name) {
        return memberRepository.findMemberByNameContainingAndIsDelFalse(name);
    }

    @Override
    public List<Member> findMemberByUsernameLike(String username) {
        return memberRepository.findMemberByUsernameContainingAndIsDelFalse(username);
    }

    @Override
    public Member findMemberByUsername(String username) {
        return memberRepository.findMemberByUsernameAndIsDelFalse(username);
    }

    @Override
    public Member findMemberById(String id) {
        return memberRepository.findMemberByIdAndIsDelFalse(id);
    }

    @Override
    public Map<String,Object> verifyMember(String username, String password) {
        password = encrypt(password,username);
        Map<String,Object> map = new HashMap<String,Object>();
        Member member = memberRepository.findMemberByUsernameAndPasswordAndIsDelFalse(username,password);
        if(member != null) {
            member.setLoginTime(new Date());
            memberRepository.save(member);
            map.put(SESSION_TOKEN_KEY,UUID.randomUUID().toString().replace("-", ""));
            map.put(MEMBER_INFO,member);
            return map;
        }
        return null;
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public Page<Member> findMember(String id, String name, String username, Pageable pageable) {
        return memberRepository.findAll(memberWhere(id,username,name),pageable);
    }

}

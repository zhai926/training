package com.lehui.config;

import com.lehui.Repository.MemberRepository;
import com.lehui.entity.Member;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lehui.utils.AESUtils.encrypt;

/**
 * 初始化数据
 * Created by SunHaiyang on 2017/6/16.
 */
@Configuration
@Log4j
public class InitMysqlConfig {
    @Autowired
    private MemberRepository memberRepository;

    @Bean
    public Member initMember(){
        long count = memberRepository.count();
        log.info("检查是否拥有数据");
        if(!(count > 0)){
            log.info("暂无数据.");
            log.info("正在初始化数据...");
            Member member = new Member("管理员","admin",encrypt("123456","admin"));
            member = memberRepository.save(member);
            log.info("初始化数据成功.");
            return member;
        }else {
            log.info("拥有数据.");
            return null;
        }
    }
}

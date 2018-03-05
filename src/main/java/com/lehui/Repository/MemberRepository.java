package com.lehui.Repository;

import com.lehui.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户持久层
 * Created by SunHaiyang on 2017/6/6.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member,String> , JpaSpecificationExecutor<Member> {

    /**
     * 通过Username查找member
     * @param username
     * @return
     */
    public Member findMemberByUsernameAndIsDelFalse(String username);

    /**
     * 通过username 模糊查询member
     * @param Username
     * @return
     */
    public List<Member> findMemberByUsernameContainingAndIsDelFalse(String Username);

    /**
     * 通过name 模糊查询member
     * @param name
     * @return
     */
    public List<Member> findMemberByNameContainingAndIsDelFalse(String name);

    /**
     * 通过username和password查询用户
     * @param username
     * @param password
     * @return
     */
    public Member findMemberByUsernameAndPasswordAndIsDelFalse(String username,String password);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Member findMemberByIdAndIsDelFalse(String id);

    /**
     * 逻辑删除用户
     * @param id
     */
    @Query(value = "update Member m set m.isDel = true where  m.id = ?1")
    @Modifying
    public void deleteMemberById(String id);


}

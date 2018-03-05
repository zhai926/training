package com.lehui.utils;

import com.lehui.entity.Brand;
import com.lehui.entity.Member;
import com.lehui.entity.News;
import com.lehui.entity.NewsClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SunHaiyang on 2017/6/9.
 */
public class SpecsUtils {

    /**
     * news 动态条件
     * @param title
     * @param members
     * @param newsClass
     * @param startDate
     * @param endDate
     * @return
     */
    public static Specification<News> newsWhere(
            final String title,
            final List<Member> members,
            final NewsClass newsClass,
            final Date startDate,
            final Date endDate
    ){
        return new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if(title != null && title.length() > 0){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+title+"%"));
                }
                if(members != null && members.size() > 0){
                    for (Member member:members
                         ) {
                        predicates.add(criteriaBuilder.equal(root.<String>get("member"),member));
                    }
                }
                if(newsClass != null){
                    predicates.add(criteriaBuilder.equal(root.<String>get("newsClass"),newsClass));
                }
                if (startDate == null && endDate != null){
                    predicates.add(criteriaBuilder.between(root.<Date>get("createTime"),startDate,endDate));
                }else if(startDate != null){
                    predicates.add(criteriaBuilder.between(root.<Date>get("createTime"),startDate,new Date()));
                }else if (endDate != null){
                    predicates.add(criteriaBuilder.between(root.<Date>get("createTime"),new Date(),endDate));
                }
                predicates.add(criteriaBuilder.equal(root.<String>get("isDel"),false));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }

    /**
     * member 动态查询
     * @param id
     * @param username
     * @param name
     * @return
     */
    public static Specification<Member> memberWhere(
            final String id,
            final String username,
            final String name
            ){
        return new Specification<Member>(){

            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if(id != null && id.length() > 0){
                    predicates.add(criteriaBuilder.equal(root.<String>get("id"),id));
                }
                if(name != null && name.length() > 0){
                    predicates.add(criteriaBuilder.like(root.<String>get("name"),"%"+name+"%"));
                }
                if(username != null && username.length() > 0){
                    predicates.add(criteriaBuilder.like(root.<String>get("username"),"%"+username+"%"));
                }
                predicates.add(criteriaBuilder.equal(root.<String>get("isDel"),false));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }

}

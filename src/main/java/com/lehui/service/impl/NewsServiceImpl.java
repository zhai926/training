package com.lehui.service.impl;

import com.lehui.Repository.NewsRepository;
import com.lehui.entity.Member;
import com.lehui.entity.NewsClass;
import com.lehui.entity.News;
import com.lehui.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.lehui.utils.SpecsUtils.*;


/**
 * Created by SunHaiyang on 2017/6/7.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News updateNews(News news) {
        return newsRepository.save(news);
    }


    @Override
    public Page<News> findByNewsClass(Pageable pageable, NewsClass newsClass) {
        return newsRepository.findByNewsClassAndIsDelFalseOrderByCreateTimeDesc(pageable,newsClass);
    }

    @Override
    public boolean deleteNewsById(String id) {
        try {
            newsRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<News> findAll(String title, List<Member> members, NewsClass newsClass, Date startDate, Date endDate, Pageable pageable) {
        return newsRepository.findAll(newsWhere(title,members,newsClass,startDate,endDate),pageable);
    }

    @Override
    public News findById(String id) {
        return newsRepository.findByIdAndIsDelFalseOrderByCreateTimeDesc(id);
    }

    @Override
    public Page<News> findSimpleNewsByNewsClass(NewsClass newsClass, Pageable pageable) {
        return newsRepository.findNewsByNewsClass(newsClass,pageable);
    }


}

package com.lehui.service.impl;

import com.lehui.Repository.NewsClassRepository;
import com.lehui.entity.NewsClass;
import com.lehui.service.NewsClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SunHaiyang on 2017/6/7.
 */
@Service
@Transactional
public class NewsClassServiceImpl implements NewsClassService {

    @Autowired
    NewsClassRepository newsClassRepository;

    @Override
    public NewsClass saveNewsClass(NewsClass newsClass) {
        return newsClassRepository.save(newsClass);
    }

    @Override
    public NewsClass updateNewsClass(NewsClass newsClass) {
        return newsClassRepository.save(newsClass);
    }

    @Override
    public boolean deleteNewsClass(String id) {
        try {
            newsClassRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NewsClass findById(String id) {
        return newsClassRepository.findByIdAndIsDelFalse(id);
    }

    @Override
    public List<NewsClass> findByName(String name) {
        return newsClassRepository.findByNameContainingAndIsDelFalse(name);
    }

    @Override
    public Page<NewsClass> findAll(Pageable pageable) {
        return newsClassRepository.findAllByIsDelFalseOrderBySort(pageable);
    }

}

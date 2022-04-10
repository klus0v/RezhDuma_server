package com.example.rezh.services;

import com.example.rezh.entities.NewsEntity;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.models.News;
import com.example.rezh.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;


    public News getOneNews(Integer id) throws NewsNotFoundException {
        NewsEntity news = newsRepository.findById(id).get();
        if (news == null){
            throw new NewsNotFoundException("Новость не найдена");
        }
        return News.toModel(news);
    }

    public Iterable<NewsEntity> getAllNews(){
        return newsRepository.findAll();
    }

    public NewsEntity postNews(NewsEntity news)  {
        return newsRepository.save(news);
    }

    public Integer deleteNews(Integer id) {
        newsRepository.deleteById(id);
        return id;
    }

}

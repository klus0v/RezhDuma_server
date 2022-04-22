package com.example.rezh.services;

import com.example.rezh.entities.NewsEntity;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.models.News;
import com.example.rezh.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;


    public NewsEntity getOneNews(Integer id) throws NewsNotFoundException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        return news.get();
    }

    public Iterable<NewsEntity> getAllNews(){
        return newsRepository.findAll();
    }

    public NewsEntity postNews(NewsEntity news)  {
        return newsRepository.save(news);
    }

    public Integer deleteNews(Integer id) throws NewsNotFoundException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        newsRepository.deleteById(id);
        return id;
    }

    public void editNews(NewsEntity newNews, Integer id) throws NewsNotFoundException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        NewsEntity currentNews = getOneNews(id);
        if (newNews.getTitle() != null)
            currentNews.setTitle(newNews.getTitle());
        if (newNews.getText() != null)
            currentNews.setText(newNews.getText());
        if (newNews.getFiles() != null)
            currentNews.setFiles(newNews.getFiles());
        if (newNews.getIsEvent() != null)
            currentNews.setIsEvent(newNews.getIsEvent());
        postNews(currentNews);
    }

}

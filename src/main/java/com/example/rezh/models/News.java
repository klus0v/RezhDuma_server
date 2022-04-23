package com.example.rezh.models;

import com.example.rezh.entities.NewsEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class News {
    private Integer id;
    private String title;
    private String text;
    private String files;
    private LocalDateTime newsDate;


    public static News toModel(NewsEntity newsEntity) {
        News news = new News();

        news.setId(newsEntity.getId());
        news.setTitle(newsEntity.getTitle());
        news.setText(newsEntity.getText());
        news.setFiles(newsEntity.getFiles());
        news.setNewsDate(newsEntity.getNewsDate());

        return news;
    }

    public static List<News> toModel(Iterable<NewsEntity> newsEntities) {
        List<News> newsModels = new ArrayList<News>();
        for (NewsEntity newsEntity: newsEntities ) {
            News news = new News();

            news.setId(newsEntity.getId());
            news.setTitle(newsEntity.getTitle());
            news.setText(newsEntity.getText());
            news.setFiles(newsEntity.getFiles());
            news.setNewsDate(newsEntity.getNewsDate());

            newsModels.add(news);
        }
        return newsModels;
    }

    public News() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public LocalDateTime getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(LocalDateTime newsDate) {
        this.newsDate = newsDate;
    }
}

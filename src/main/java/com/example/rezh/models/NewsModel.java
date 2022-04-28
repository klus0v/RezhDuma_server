package com.example.rezh.models;

import com.example.rezh.entities.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsModel {
    private Long id;
    private String title;
    private String text;
    private String files;
    private LocalDateTime newsDate;


    public static NewsModel toModel(News newsEntity) {
        NewsModel news = new NewsModel();

        news.setId(newsEntity.getId());
        news.setTitle(newsEntity.getTitle());
        news.setText(newsEntity.getText());
        news.setFiles(newsEntity.getFiles());
        news.setNewsDate(newsEntity.getNewsDate());

        return news;
    }

    public static List<NewsModel> toModel(Iterable<News> newsEntities) {
        List<NewsModel> newsModels = new ArrayList<NewsModel>();
        for (News newsEntity: newsEntities ) {
            NewsModel news = new NewsModel();

            news.setId(newsEntity.getId());
            news.setTitle(newsEntity.getTitle());
            news.setText(newsEntity.getText());
            news.setFiles(newsEntity.getFiles());
            news.setNewsDate(newsEntity.getNewsDate());

            newsModels.add(news);
        }
        return newsModels;
    }

}

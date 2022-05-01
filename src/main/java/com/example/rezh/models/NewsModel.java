package com.example.rezh.models;


import com.example.rezh.entities.News;
import com.example.rezh.entities.AllFiles;
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
    private ArrayList<String> filesNames;
    private LocalDateTime newsDate;


    public static NewsModel toModel(News newsEntity) {
        NewsModel news = new NewsModel();

        news.setId(newsEntity.getId());
        news.setTitle(newsEntity.getTitle());
        news.setText(newsEntity.getText());
        news.setNewsDate(newsEntity.getNewsDate());
        news.setFilesNames(new ArrayList<>());

        var files = newsEntity.getFiles();
        for (AllFiles file : files) {
            news.filesNames.add(file.getFileName());
        }

        return news;
    }

    public static List<NewsModel> toModel(Iterable<News> newsEntities) {
        List<NewsModel> newsModels = new ArrayList<>();
        for (News newsEntity: newsEntities ) {
            newsModels.add(toModel(newsEntity));
        }
        return newsModels;
    }

}

package com.example.rezh.services;


import com.example.rezh.entities.News;
import com.example.rezh.entities.AllFiles;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public News getOneNews(Long id) throws NewsNotFoundException {
        if (newsRepository.findById(id).isEmpty())
            throw new NewsNotFoundException("Новость не найдена");

        return newsRepository.getById(id);
    }

    public Iterable<News> getAllNews(){
        return newsRepository.findAll();
    }

    public Iterable<News> getEvents() {
        return newsRepository.findAllByEvent(true);
    }

    public void postNews(String title, String text, Boolean event, ArrayList<MultipartFile> files) throws IOException {
        News news = new News();
        news.setTitle(title);
        news.setText(text);
        news.setEvent(event);
        addFiles(files, news);
        newsRepository.save(news);
    }

    public void deleteNews(Long id) throws NewsNotFoundException {
        if (newsRepository.findById(id).isEmpty())
            throw new NewsNotFoundException("Новость не найдена");

        newsRepository.deleteById(id);
    }

    public void editNews(String title, String text, Boolean event, ArrayList<MultipartFile> files, Long id) throws NewsNotFoundException, IOException {

        if (newsRepository.findById(id).isEmpty())
            throw new NewsNotFoundException("Новость не найдена");

        News currentNews = getOneNews(id);
        if (title != null)
            currentNews.setTitle(title);
        if (text != null)
            currentNews.setText(text);
        currentNews.setEvent(event);
        if (files != null)
            addFiles(files, currentNews);

        newsRepository.save(currentNews);
    }

    private void addFiles(ArrayList<MultipartFile> files, News news) throws IOException {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFileName));

                AllFiles newsFile = new AllFiles();
                newsFile.setFileName(uploadPath + "/" + resultFileName);
                newsFile.setNews(news);
                news.addFile(newsFile);
            }
        }
    }
}


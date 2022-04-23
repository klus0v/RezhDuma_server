package com.example.rezh.services;

import com.example.rezh.entities.NewsEntity;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String uploadPath;


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

    public NewsEntity postNews(String title, String text, String isEvent, MultipartFile file) throws IOException {
        NewsEntity news = new NewsEntity();
        news.setTitle(title);
        news.setText(text);
        news.setIsEvent(isEvent);
        news.setFiles(addFile(file));

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

    public void editNews(String title, String text, String isEvent, MultipartFile file, Integer id) throws NewsNotFoundException, IOException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        NewsEntity currentNews = getOneNews(id);
        if (title != null)
            currentNews.setTitle(title);
        if (text != null)
            currentNews.setText(text);
        if (!file.isEmpty())
            currentNews.setFiles(addFile(file));
        if (isEvent != null)
            currentNews.setIsEvent(isEvent);
        newsRepository.save(currentNews);
    }

    private String addFile(MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists())
                uploadDirectory.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));
            return (uploadPath + "/" + resultFileName);
        }
        return null;
    }
}


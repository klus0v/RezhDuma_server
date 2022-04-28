package com.example.rezh.services;

import com.example.rezh.entities.News;
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


    public News getOneNews(Long id) throws NewsNotFoundException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        return news.get();
    }

    public Iterable<News> getAllNews(){
        return newsRepository.findAll();
    }

    public News postNews(String title, String text, Boolean event, MultipartFile file) throws IOException {
        News news = new News();
        news.setTitle(title);
        news.setText(text);
        news.setEvent(event);
        news.setFiles(addFile(file));

        return newsRepository.save(news);
    }

    public Long deleteNews(Long id) throws NewsNotFoundException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        newsRepository.deleteById(id);
        return id;
    }

    public void editNews(String title, String text, Boolean event, MultipartFile file, Long id) throws NewsNotFoundException, IOException {
        var news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NewsNotFoundException("Новость не найдена");
        }
        News currentNews = getOneNews(id);
        if (title != null)
            currentNews.setTitle(title);
        if (text != null)
            currentNews.setText(text);
        if (event)
            currentNews.setEvent(event);
        currentNews.setFiles(addFile(file));

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

    public Iterable<News> getEvents() {
        return newsRepository.findAllByEvent(true);
    }
}


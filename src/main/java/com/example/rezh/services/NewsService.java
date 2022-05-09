package com.example.rezh.services;


import com.example.rezh.entities.News;
import com.example.rezh.entities.File;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News getOneNews(Long id) throws NewsNotFoundException {
        if (newsRepository.findById(id).isEmpty())
            throw new NewsNotFoundException("Новость не найдена");

        return newsRepository.getById(id);
    }

    public List<News> getNewsPagination(Integer page, Integer count) {

        List<News> news = getAllNews();
        List<News> currentNews = new ArrayList<>();
        for (int i = (page-1)*count; i <=page*count; i++) {
            if (i > news.size() - 1)
                break;
            currentNews.add(news.get(i));
        }
        return currentNews;
    }

    public List<News> getEventsPagination(Integer page, Integer count) {
        List<News> news = getAllEvents();
        List<News> currentEvents = new ArrayList<>();
        for (int i = (page-1)*count; i <=page*count  ; i++) {
            if (i > news.size()-1)
                break;
            currentEvents.add(news.get(i));
        }
        return currentEvents;
    }

    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    public List<News> getAllEvents() {
        return newsRepository.findAllByEvent();
    }

    public void postNews(String title, String text, Boolean event, ArrayList<MultipartFile> files) throws IOException {
        News news = new News();
        news.setTitle(title);
        news.setText(text);
        news.setEvent(event);
        if (files != null)
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

                file.transferTo(new java.io.File(uploadPath + "/" + resultFileName));

                File newsFile = new File();
                newsFile.setFileName(uploadPath + "/" + resultFileName);
                newsFile.setNews(news);
                news.addFile(newsFile);
            }
        }
    }


}


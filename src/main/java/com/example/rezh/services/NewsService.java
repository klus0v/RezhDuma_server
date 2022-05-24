package com.example.rezh.services;


import com.example.rezh.entities.News;
import com.example.rezh.entities.File;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileStore fileStore;




    @Autowired
    public NewsService(NewsRepository newsRepository, FileStore fileStore) {
        this.newsRepository = newsRepository;
        this.fileStore = fileStore;
    }

    public News getOneNews(Long id) throws NewsNotFoundException {
        if (newsRepository.findById(id).isEmpty())
            throw new NewsNotFoundException("Новость не найдена");

        return newsRepository.getById(id);
    }

    public List<News> getNewsPaginationSearch(Integer page, Integer count, String find) {

        List<News> news;

        if (find == null)
            news = getAllNews();

        else
            news = newsRepository.findNews( "%" + find + "%");

        if (page != null && count != null) {
            List<News> currentNews = new ArrayList<>();
            for (int i = (page - 1) * count; i < page * count; i++) {
                if (i > news.size() - 1)
                    break;
                currentNews.add(news.get(i));
            }
            return currentNews;
        }
        return news;
    }

    public List<News> getEventsPagination(Integer page, Integer count) {
        List<News> news = getAllEvents();
        List<News> currentEvents = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count  ; i++) {
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

    public void postNews(String title, String text, Boolean event, ArrayList<MultipartFile> files) {
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

    public void editNews(String title, String text, Boolean event, ArrayList<MultipartFile> files, Long id) throws NewsNotFoundException {

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

    private void addFiles(ArrayList<MultipartFile> files, News news)  {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                Map<String, String> metadata = extractMetadata(file);
                String path = "rezh3545";
                String filename = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
                fileStore.save(path, filename, file);



                File newsFile = new File();
                newsFile.setFileName("https://storage.yandexcloud.net/rezh3545/" + filename);
                newsFile.setNews(news);
                news.addFile(newsFile);
            }
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }



}


package com.example.rezh.controllers;


import com.example.rezh.entities.NewsEntity;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.models.News;
import com.example.rezh.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @GetMapping(value = "{id}")
    public ResponseEntity getOneNews(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(News.toModel(newsService.getOneNews(id)));
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getAllNews() {
        try {
            return ResponseEntity.ok(News.toModel(newsService.getAllNews()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postNews(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false) String isEvent,
                                   @RequestParam(required = false) MultipartFile file) {
        try {
            newsService.postNews(title, text, isEvent, file);
            return ResponseEntity.ok("Новость добавлена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteNews(@PathVariable Integer id) {
        try {
            newsService.deleteNews(id);
            return ResponseEntity.ok("Новость удалена");
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editNews(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false) String isEvent,
                                   @RequestParam(required = false) MultipartFile files,
                                   @PathVariable Integer id) {
        try {
            newsService.editNews(title, text, isEvent, files, id);
            return ResponseEntity.ok("Новость изменена");
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

}
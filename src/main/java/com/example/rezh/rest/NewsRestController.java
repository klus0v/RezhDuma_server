package com.example.rezh.rest;


import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.models.NewsModel;
import com.example.rezh.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/news")
public class NewsRestController {

    private final NewsService newsService;


    @GetMapping(value = "{id}")
    public ResponseEntity getOneNews(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(NewsModel.toModel(newsService.getOneNews(id)));
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getNews(@RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer count) {
        try {
            if (page == null || count == null)
                return ResponseEntity.ok(NewsModel.toModel(newsService.getAllNews()));
            return ResponseEntity.ok(NewsModel.toModel(newsService.getNewsPagination(page, count)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    @RequestMapping("/events")
    public ResponseEntity getEvents(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer count) {
        try {
            if (page == null || count == null)
                return ResponseEntity.ok().body(NewsModel.toModel(newsService.getAllEvents()));
            return ResponseEntity.ok(NewsModel.toModel(newsService.getEventsPagination(page, count)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity<String> postNews(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false, defaultValue = "0") Boolean event,
                                   @RequestParam(required = false) ArrayList<MultipartFile> files) {
        try {
            newsService.postNews(title, text, event, files);
            return ResponseEntity.ok("Новость добавлена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNews(id);
            return ResponseEntity.ok("Новость " + id + " удалена");
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<String> editNews(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false, defaultValue = "0") Boolean event,
                                   @RequestParam(required = false) ArrayList<MultipartFile> files,
                                   @PathVariable Long id) {
        try {
            newsService.editNews(title, text, event, files, id);
            return ResponseEntity.ok("Новость " + id + " изменена");
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

}
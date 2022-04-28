package com.example.rezh.rest;



import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.models.NewsModel;
import com.example.rezh.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api/news")
public class NewsRestController {

    @Autowired
    private NewsService newsService;


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
    public ResponseEntity getAllNews() {
        try {
            return ResponseEntity.ok(NewsModel.toModel(newsService.getAllNews()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    @RequestMapping("/events")
    public ResponseEntity getAllEvents() {
        try {
            return ResponseEntity.ok().body(NewsModel.toModel(newsService.getEvents()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postNews(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false) Boolean event,
                                   @RequestParam(required = false) MultipartFile files) {
        try {
            newsService.postNews(title, text, event, files);
            return ResponseEntity.ok("Новость добавлена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNews(id);
            return ResponseEntity.ok("Новость " + id + " удалена");
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editNews(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false) Boolean event,
                                   @RequestParam(required = false) MultipartFile files,
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
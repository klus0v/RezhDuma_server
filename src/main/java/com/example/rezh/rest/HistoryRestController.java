package com.example.rezh.rest;


import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.models.HistoryModel;
import com.example.rezh.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/history")
public class HistoryRestController {

    @Autowired
    private HistoryService historyService;


    @GetMapping(value = "{id}")
    public ResponseEntity getOneHistory(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(HistoryModel.toModel(historyService.getOneHistory(id)));
        } catch (HistoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getAllHistory() {
        try {
            return ResponseEntity.ok().body(HistoryModel.toModel(historyService.getAllHistory()));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postHistory(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) MultipartFile files) {
        try {
            historyService.postHistory(title, text, files);
            return ResponseEntity.ok("История добавлена");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteHistory(@PathVariable Long id) {
        try {
            historyService.deleteHistory(id);
            return ResponseEntity.ok("История " + id + " удалена");
        } catch (HistoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity editHistory(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) MultipartFile files,
                                      @PathVariable Long id) {
        try {
            historyService.editHistory(title, text, files, id);
            return ResponseEntity.ok("История " + id + " изменена");
        } catch (HistoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
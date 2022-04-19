package com.example.rezh.controllers;


import com.example.rezh.entities.HistoryEntity;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.models.History;
import com.example.rezh.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;


    @GetMapping(params = {"id"})
    public ResponseEntity getOneHistory(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(History.toModel(historyService.getOneHistory(id)));
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
            return ResponseEntity.ok(History.toModel(historyService.getAllHistory()));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postHistory(@RequestBody HistoryEntity history) {
        try {
            historyService.postHistory(history);
            return ResponseEntity.ok("История добавлена");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity deleteHistory(@RequestParam Integer id) {
        try {
            historyService.deleteHistory(id);
            return ResponseEntity.ok("История удалена");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editHistory(@RequestBody HistoryEntity history, @PathVariable Integer id) {
        try {
            historyService.editHistory(history, id);
            return ResponseEntity.ok("История изменена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
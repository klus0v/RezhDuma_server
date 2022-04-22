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


    @GetMapping(value = "{id}")
    public ResponseEntity getOneHistory(@PathVariable Integer id) {
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

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteHistory(@PathVariable Integer id) {
        try {
            historyService.deleteHistory(id);
            return ResponseEntity.ok("История удалена");
        } catch (HistoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editHistory(@RequestBody HistoryEntity history,
                                      @PathVariable Integer id) {
        try {
            historyService.editHistory(history, id);
            return ResponseEntity.ok("История изменена");
        } catch (HistoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
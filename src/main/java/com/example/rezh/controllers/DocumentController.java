package com.example.rezh.controllers;



import com.example.rezh.entities.DocumentEntity;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.models.Document;
import com.example.rezh.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @GetMapping(params = {"id"})
    public ResponseEntity getOneDocument(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(Document.toModel(documentService.getOneDocument(id)));
        } catch (DocumentNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getAllDocuments() {
        try {
            return ResponseEntity.ok(Document.toModel(documentService.getAllDocuments()));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postDocument(@RequestBody DocumentEntity document) {
        try {
            documentService.postDocument(document);
            return ResponseEntity.ok("Документ добавлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity deleteDocument(@RequestParam Integer id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok("Документ удален");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editDocument(@RequestBody DocumentEntity document, @PathVariable Integer id) {
        try {
            documentService.editDocument(document, id);
            return ResponseEntity.ok("Документ изменен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
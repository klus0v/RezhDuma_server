package com.example.rezh.rest;


import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.models.DocumentModel;
import com.example.rezh.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/documents")
public class DocumentRestController {

    private final DocumentService documentService;

    @GetMapping(value = "{id}")
    public ResponseEntity getOneDocument(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(DocumentModel.toModel(documentService.getOneDocument(id)));
        } catch (DocumentNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getDocuments(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer count) {
        try {
            if (page == null || count == null)
                return ResponseEntity.ok(DocumentModel.toModel(documentService.getAllDocuments()));
            return ResponseEntity.ok(DocumentModel.toModel(documentService.getDocumentsPagination(page, count)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity<String> postDocument(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) String text,
                                       @RequestParam(required = false) ArrayList<MultipartFile> files) {
        try {
            documentService.postDocument(title, text, files);
            return ResponseEntity.ok("Документ добавлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok("Документ " + id + " удален");
        } catch (DocumentNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<String> editDocument(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) String text,
                                       @RequestParam(required = false) ArrayList<MultipartFile> files,
                                       @PathVariable Long id) {
        try {
            documentService.editDocument(title, text, files, id);
            return ResponseEntity.ok("Документ " + id + " изменен");
        } catch (DocumentNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
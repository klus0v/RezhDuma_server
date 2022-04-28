package com.example.rezh.rest;



import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.models.DocumentModel;
import com.example.rezh.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/documents")
public class DocumentRestController {

    @Autowired
    private DocumentService documentService;


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
    public ResponseEntity getAllDocuments() {
        try {
            return ResponseEntity.ok().body(DocumentModel.toModel(documentService.getAllDocuments()));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postDocument(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) String text,
                                       @RequestParam(required = false) MultipartFile files) {
        try {
            documentService.postDocument(title, text, files);
            return ResponseEntity.ok("Документ добавлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteDocument(@PathVariable Long id) {
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

    @PutMapping(value = "{id}")
    public ResponseEntity editDocument(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) String text,
                                       @RequestParam(required = false) MultipartFile files,
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
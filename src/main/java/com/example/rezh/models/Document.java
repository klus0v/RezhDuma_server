package com.example.rezh.models;

import com.example.rezh.entities.DocumentEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Document {
    private Integer id;
    private String title;
    private String text;
    private String files;
    private LocalDateTime documentDate;


    public static Document toModel(DocumentEntity documentEntity) {
        Document document = new Document();

        document.setId(documentEntity.getId());
        document.setTitle(documentEntity.getTitle());
        document.setText(documentEntity.getText());
        document.setFiles(documentEntity.getFiles());
        document.setDocumentDate(documentEntity.getDocumentDate());

        return document;
    }

    public static List<Document> toModel(Iterable<DocumentEntity> documentEntities) {
        List<Document> documentModels = new ArrayList<Document>();
        for (DocumentEntity documentEntity: documentEntities ) {
            Document document = new Document();

            document.setId(documentEntity.getId());
            document.setTitle(documentEntity.getTitle());
            document.setText(documentEntity.getText());
            document.setFiles(documentEntity.getFiles());
            document.setDocumentDate(documentEntity.getDocumentDate());

            documentModels.add(document);
        }
        return documentModels;
    }

    public Document() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }
}

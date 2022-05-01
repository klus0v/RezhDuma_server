package com.example.rezh.models;


import com.example.rezh.entities.AllFiles;
import com.example.rezh.entities.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentModel {
    private Long id;
    private String title;
    private String text;
    private ArrayList<String> filesNames;
    private LocalDateTime documentDate;


    public static DocumentModel toModel(Document documentEntity) {
        DocumentModel document = new DocumentModel();

        document.setId(documentEntity.getId());
        document.setTitle(documentEntity.getTitle());
        document.setText(documentEntity.getText());
        document.setDocumentDate(documentEntity.getDocumentDate());
        document.setFilesNames(new ArrayList<>());

        var files = documentEntity.getFiles();
        for (AllFiles file : files) {
            document.filesNames.add(file.getFileName());
        }

        return document;
    }

    public static List<DocumentModel> toModel(Iterable<Document> documentEntities) {
        List<DocumentModel> documentModels = new ArrayList<DocumentModel>();
        for (Document documentEntity: documentEntities ) {
            documentModels.add(toModel(documentEntity));
        }
        return documentModels;
    }

}

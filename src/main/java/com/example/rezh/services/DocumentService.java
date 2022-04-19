package com.example.rezh.services;


import com.example.rezh.entities.DocumentEntity;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    public DocumentEntity getOneDocument(Integer id) throws DocumentNotFoundException {
        DocumentEntity document = documentRepository.findById(id).get();
        if (document == null){
            throw new DocumentNotFoundException("Проект не найден");
        }
        return document;
    }

    public Iterable<DocumentEntity> getAllDocuments(){
        return documentRepository.findAll();
    }

    public DocumentEntity postDocument(DocumentEntity document)  {
        return documentRepository.save(document);
    }

    public Integer deleteDocument(Integer id) {
        documentRepository.deleteById(id);
        return id;
    }

    public void editDocument(DocumentEntity newDocument, Integer id) throws DocumentNotFoundException {
        DocumentEntity currentDocument = getOneDocument(id);
        if (newDocument.getTitle() != null)
            currentDocument.setTitle(newDocument.getTitle());
        if (newDocument.getText() != null)
            currentDocument.setText(newDocument.getText());
        if (newDocument.getFiles() != null)
            currentDocument.setFiles(newDocument.getFiles());
        postDocument(currentDocument);
    }

}

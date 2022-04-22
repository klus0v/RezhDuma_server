package com.example.rezh.services;


import com.example.rezh.entities.DocumentEntity;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    public DocumentEntity getOneDocument(Integer id) throws DocumentNotFoundException {
        var document = documentRepository.findById(id);
        if (document.isEmpty()){
            throw new DocumentNotFoundException("Документ не найден");
        }
        return document.get();
    }

    public Iterable<DocumentEntity> getAllDocuments(){
        return documentRepository.findAll();
    }

    public DocumentEntity postDocument(DocumentEntity document)  {
        return documentRepository.save(document);
    }

    public Integer deleteDocument(Integer id) throws DocumentNotFoundException{
        var document = documentRepository.findById(id);
        if (document.isEmpty()){
            throw new DocumentNotFoundException("Документ не найден");
        }
        documentRepository.deleteById(id);
        return id;
    }

    public void editDocument(DocumentEntity newDocument, Integer id) throws DocumentNotFoundException {
        var document = documentRepository.findById(id);
        if (document.isEmpty()){
            throw new DocumentNotFoundException("Документ не найден");
        }
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

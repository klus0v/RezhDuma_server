package com.example.rezh.services;


import com.example.rezh.entities.DocumentEntity;
import com.example.rezh.entities.HistoryEntity;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Value("${upload.path}")
    private String uploadPath;


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

    public DocumentEntity postDocument(String title, String text, MultipartFile file) throws IOException {
        DocumentEntity document = new DocumentEntity();

        document.setTitle(title);
        document.setText(text);
        document.setFiles(addFile(file));

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

    public void editDocument(String title, String text, MultipartFile file, Integer id) throws DocumentNotFoundException, IOException {
        var document = documentRepository.findById(id);
        if (document.isEmpty()){
            throw new DocumentNotFoundException("Документ не найден");
        }
        DocumentEntity currentDocument = getOneDocument(id);
        if (title != null)
            currentDocument.setTitle(title);
        if (text != null)
            currentDocument.setText(text);
        if (!file.isEmpty())
            currentDocument.setFiles(addFile(file));
        documentRepository.save(currentDocument);
    }


    private String addFile(MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists())
                uploadDirectory.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));
            return (uploadPath + "/" + resultFileName);
        }
        return null;
    }

}

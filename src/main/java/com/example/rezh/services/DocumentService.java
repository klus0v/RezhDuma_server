package com.example.rezh.services;


import com.example.rezh.entities.File;
import com.example.rezh.entities.Document;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document getOneDocument(Long id) throws DocumentNotFoundException {
        if (documentRepository.findById(id).isEmpty())
            throw new DocumentNotFoundException("Документ не найден");

        return documentRepository.getById(id);
    }

    public List<Document> getDocumentsPagination(Integer page, Integer count) {
        List<Document> documents = getAllDocuments();
        List<Document> currentDocuments = new ArrayList<>();
        for (int i = (page-1)*count; i <=page*count; i++) {
            if (i > documents.size() - 1)
                break;
            currentDocuments.add(documents.get(i));
        }
        return currentDocuments;
    }

    public List<Document> getAllDocuments(){
        return documentRepository.findAll();
    }

    public void deleteDocument(Long id) throws DocumentNotFoundException{
        if (documentRepository.findById(id).isEmpty())
            throw new DocumentNotFoundException("Документ не найден");

        documentRepository.deleteById(id);
    }

    public void editDocument(String title, String text, ArrayList<MultipartFile> files, Long id) throws DocumentNotFoundException, IOException {
        if (documentRepository.findById(id).isEmpty())
            throw new DocumentNotFoundException("Документ не найден");

        Document currentDocument = getOneDocument(id);
        if (title != null)
            currentDocument.setTitle(title);
        if (text != null)
            currentDocument.setText(text);
        if (files != null)
            addFiles(files, currentDocument);

        documentRepository.save(currentDocument);
    }

    public void postDocument(String title, String text, ArrayList<MultipartFile> files) throws IOException {
        Document document = new Document();
        document.setTitle(title);
        document.setText(text);
        if (files != null)
            addFiles(files, document);
        documentRepository.save(document);
    }

    private void addFiles(ArrayList<MultipartFile> files, Document document) throws IOException {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new java.io.File(uploadPath + "/" + resultFileName));

                File documentFile = new File();
                documentFile.setFileName(uploadPath + "/" + resultFileName);
                documentFile.setDocument(document);
                document.addFile(documentFile);
            }
        }
    }
}

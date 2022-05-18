package com.example.rezh.services;


import com.example.rezh.entities.File;
import com.example.rezh.entities.Document;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStore fileStore;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, FileStore fileStore) {
        this.documentRepository = documentRepository;
        this.fileStore = fileStore;
    }

    public Document getOneDocument(Long id) throws DocumentNotFoundException {
        if (documentRepository.findById(id).isEmpty())
            throw new DocumentNotFoundException("Документ не найден");

        return documentRepository.getById(id);
    }

    public List<Document> getDocumentsPagination(Integer page, Integer count, String find) {
        List<Document> documents;

        if (find == null)
            documents = getAllDocuments();

        else
            documents = documentRepository.findAllByTitleContainingOrTextContaining("%" + find + "%", "%" + find + "%");

        if (page != null && count != null) {
            List<Document> currentDocuments = new ArrayList<>();
            for (int i = (page - 1) * count; i < page * count; i++) {
                if (i > documents.size() - 1)
                    break;
                currentDocuments.add(documents.get(i));
            }
            return currentDocuments;
        }
        return documents;
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

    public void postDocument(String title, String text, ArrayList<MultipartFile> files) {
        Document document = new Document();
        document.setTitle(title);
        document.setText(text);
        if (files != null)
            addFiles(files, document);
        documentRepository.save(document);
    }

    private void addFiles(ArrayList<MultipartFile> files, Document document) {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                Map<String, String> metadata = extractMetadata(file);
                String path = "rezh3545";
                String filename = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

                try {
                    fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }


                File documentFile = new File();
                documentFile.setFileName("https://storage.yandexcloud.net/rezh3545/" + filename);
                documentFile.setDocument(document);
                document.addFile(documentFile);
            }
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }
}

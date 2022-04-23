package com.example.rezh.services;

import com.example.rezh.entities.HistoryEntity;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.models.History;
import com.example.rezh.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Value("${upload.path}")
    private String uploadPath;


    public HistoryEntity getOneHistory(Integer id) throws HistoryNotFoundException {
        var history = historyRepository.findById(id);
        if (history.isEmpty()){
            throw new HistoryNotFoundException("История не найдена");
        }

        return history.get();
    }

    public Iterable<HistoryEntity> getAllHistory(){
        return historyRepository.findAll();
    }

    public HistoryEntity postHistory(String title, String text, MultipartFile file) throws IOException {
        HistoryEntity history = new HistoryEntity();

        history.setTitle(title);
        history.setText(text);
        history.setFiles(addFile(file));

        return historyRepository.save(history);
    }

    public Integer deleteHistory(Integer id) throws HistoryNotFoundException {
        var history = historyRepository.findById(id);
        if (history.isEmpty()){
            throw new HistoryNotFoundException("История не найдена");
        }
        historyRepository.deleteById(id);
        return id;
    }

    public void editHistory(String title, String text, MultipartFile file, Integer id) throws HistoryNotFoundException, IOException {
        var history = historyRepository.findById(id);
        if (history.isEmpty()){
            throw new HistoryNotFoundException("История не найдена");
        }
        HistoryEntity currentHistory = getOneHistory(id);
        if (title != null)
            currentHistory.setTitle(title);
        if (text != null)
            currentHistory.setText(text);
        if (!file.isEmpty())
            currentHistory.setFiles(addFile(file));
        historyRepository.save(currentHistory);
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

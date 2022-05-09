package com.example.rezh.services;


import com.example.rezh.entities.File;
import com.example.rezh.entities.History;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public History getOneHistory(Long id) throws HistoryNotFoundException {
        if (historyRepository.findById(id).isEmpty())
            throw new HistoryNotFoundException("История не найдена");

        return historyRepository.getById(id);
    }

    public List<History> getHistoryPagination(Integer page, Integer count) {
        List<History> history = getAllHistory();
        List<History> currentHistory = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > history.size() - 1)
                break;
            currentHistory.add(history.get(i));
        }
        return currentHistory;
    }

    public List<History> getAllHistory(){
        return historyRepository.findAll();
    }

    public void postHistory(String title, String text, ArrayList<MultipartFile> files) throws IOException {
        History history = new History();
        history.setTitle(title);
        history.setText(text);
        if (files != null)
            addFiles(files, history);
        historyRepository.save(history);
    }

    public void deleteHistory(Long id) throws HistoryNotFoundException {
        if (historyRepository.findById(id).isEmpty())
            throw new HistoryNotFoundException("История не найдена");

        historyRepository.deleteById(id);
    }

    public void editHistory(String title, String text, ArrayList<MultipartFile> files, Long id) throws HistoryNotFoundException, IOException {
        if (historyRepository.findById(id).isEmpty())
            throw new HistoryNotFoundException("История не найдена");

        History currentHistory = getOneHistory(id);
        if (title != null)
            currentHistory.setTitle(title);
        if (text != null)
            currentHistory.setText(text);
        if (files != null)
            addFiles(files, currentHistory);

        historyRepository.save(currentHistory);
    }

    private void addFiles(ArrayList<MultipartFile> files, History history) throws IOException {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new java.io.File(uploadPath + "/" + resultFileName));

                File historyFile = new File();
                historyFile.setFileName(uploadPath + "/" + resultFileName);
                historyFile.setHistory(history);
                history.addFile(historyFile);
            }
        }
    }
}

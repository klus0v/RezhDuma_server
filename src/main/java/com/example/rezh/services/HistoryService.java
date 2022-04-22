package com.example.rezh.services;

import com.example.rezh.entities.HistoryEntity;
import com.example.rezh.exceptions.DocumentNotFoundException;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.models.History;
import com.example.rezh.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;


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

    public HistoryEntity postHistory(HistoryEntity history)  {
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

    public void editHistory(HistoryEntity newHistory, Integer id) throws HistoryNotFoundException {
        var history = historyRepository.findById(id);
        if (history.isEmpty()){
            throw new HistoryNotFoundException("История не найдена");
        }
        HistoryEntity currentHistory = getOneHistory(id);
        if (newHistory.getTitle() != null)
            currentHistory.setTitle(newHistory.getTitle());
        if (newHistory.getText() != null)
            currentHistory.setText(newHistory.getText());
        if (newHistory.getFiles() != null)
            currentHistory.setFiles(newHistory.getFiles());
        postHistory(currentHistory);
    }

}

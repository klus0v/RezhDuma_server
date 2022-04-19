package com.example.rezh.models;

import com.example.rezh.entities.HistoryEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class History {
    private Integer id;
    private String title;
    private String text;
    private String files;
    private LocalDateTime historyDate;


    public static History toModel(HistoryEntity historyEntity) {
        History history = new History();

        history.setId(historyEntity.getId());
        history.setTitle(historyEntity.getTitle());
        history.setText(historyEntity.getText());
        history.setFiles(historyEntity.getFiles());
        history.setHistoryDate(historyEntity.getHistoryDate());

        return history;
    }

    public static List<History> toModel(Iterable<HistoryEntity> historyEntities) {
        List<History> historyModels = new ArrayList<History>();
        for (HistoryEntity historyEntity: historyEntities ) {
            History history = new History();

            history.setId(historyEntity.getId());
            history.setTitle(historyEntity.getTitle());
            history.setText(historyEntity.getText());
            history.setFiles(historyEntity.getFiles());
            history.setHistoryDate(historyEntity.getHistoryDate());

            historyModels.add(history);
        }
        return historyModels;
    }

    public History() {

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

    public LocalDateTime getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(LocalDateTime historyDate) {
        this.historyDate = historyDate;
    }
}

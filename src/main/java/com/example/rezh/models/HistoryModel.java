package com.example.rezh.models;


import com.example.rezh.entities.File;
import com.example.rezh.entities.History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryModel {
    private Long id;
    private String title;
    private String text;
    private ArrayList<String> filesNames;
    private LocalDateTime historyDate;


    public static HistoryModel toModel(History historyEntity) {
        HistoryModel history = new HistoryModel();

        history.setId(historyEntity.getId());
        history.setTitle(historyEntity.getTitle());
        history.setText(historyEntity.getText());
        history.setHistoryDate(historyEntity.getHistoryDate());
        history.setFilesNames(new ArrayList<>());

        var files = historyEntity.getFiles();
        for (File file : files) {
            history.filesNames.add(file.getFileName());
        }

        return history;
    }

    public static List<HistoryModel> toModel(List<History> historyEntities) {
        List<HistoryModel> historyModels = new ArrayList<>();
        for (History historyEntity: historyEntities ) {
            historyModels.add(toModel(historyEntity));
        }
        return historyModels;
    }

}

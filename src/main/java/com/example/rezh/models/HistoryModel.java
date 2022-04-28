package com.example.rezh.models;

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
    private String files;
    private LocalDateTime historyDate;


    public static HistoryModel toModel(History historyEntity) {
        HistoryModel history = new HistoryModel();

        history.setId(historyEntity.getId());
        history.setTitle(historyEntity.getTitle());
        history.setText(historyEntity.getText());
        history.setFiles(historyEntity.getFiles());
        history.setHistoryDate(historyEntity.getHistoryDate());

        return history;
    }

    public static List<HistoryModel> toModel(Iterable<History> historyEntities) {
        List<HistoryModel> historyModels = new ArrayList<>();
        for (History historyEntity: historyEntities ) {
            HistoryModel history = new HistoryModel();

            history.setId(historyEntity.getId());
            history.setTitle(historyEntity.getTitle());
            history.setText(historyEntity.getText());
            history.setFiles(historyEntity.getFiles());
            history.setHistoryDate(historyEntity.getHistoryDate());

            historyModels.add(history);
        }
        return historyModels;
    }

}

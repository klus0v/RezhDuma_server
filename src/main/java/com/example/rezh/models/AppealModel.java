package com.example.rezh.models;


import com.example.rezh.entities.File;
import com.example.rezh.entities.Appeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppealModel {
    private Long id;
    private String text;
    private LocalDateTime appealDate;
    private String type;
    private String district;
    private String topic;
    private String requesterName;
    private String responsibleName;
    private String response;
    private LocalDateTime responseDate;
    private ArrayList<String> filesNames;


    public static AppealModel toModel(Appeal appealEntity, boolean addUser ) {
        AppealModel appeal = new AppealModel();

        appeal.setId(appealEntity.getId());
        appeal.setText(appealEntity.getText());
        appeal.setAppealDate(appealEntity.getAppealDate());
        appeal.setResponsibleName(appealEntity.getResponsibleName());
        appeal.setResponse(appealEntity.getResponse());
        appeal.setType(appealEntity.getType());
        appeal.setDistrict(appealEntity.getDistrictTag());
        appeal.setTopic(appealEntity.getTopicTag());
        appeal.setResponseDate(appealEntity.getResponseDate());
        appeal.setFilesNames(new ArrayList<>());

        var userName = appealEntity.getUser().getLastName() + " " + appealEntity.getUser().getFirstName();

        if (addUser)
            appeal.setRequesterName(userName);

        var files = appealEntity.getFiles();
        for (File file : files) {
            appeal.filesNames.add(file.getFileName());
        }

        return appeal;
    }

    public static List<AppealModel> toModel(List<Appeal> appealEntities, boolean addUser) {
        List<AppealModel> appealModels = new ArrayList<>();
        for (Appeal appealEntity: appealEntities ) {
            appealModels.add(toModel(appealEntity, addUser));
        }
        return appealModels;
    }
}

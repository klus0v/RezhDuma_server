package com.example.rezh.models;

import com.example.rezh.entities.ProjectEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private Integer id;
    private String title;
    private String text;
    private String files;
    private LocalDateTime projectDate;


    public static Project toModel(ProjectEntity projectEntity) {
        Project project = new Project();

        project.setId(projectEntity.getId());
        project.setTitle(projectEntity.getTitle());
        project.setText(projectEntity.getText());
        project.setFiles(projectEntity.getFiles());
        project.setProjectDate(projectEntity.getProjectDate());

        return project;
    }

    public static List<Project> toModel(Iterable<ProjectEntity> projectEntities) {
        List<Project> projectModels = new ArrayList<Project>();
        for (ProjectEntity projectEntity: projectEntities ) {
            Project project = new Project();

            project.setId(projectEntity.getId());
            project.setTitle(projectEntity.getTitle());
            project.setText(projectEntity.getText());
            project.setFiles(projectEntity.getFiles());
            project.setProjectDate(projectEntity.getProjectDate());

            projectModels.add(project);
        }
        return projectModels;
    }

    public Project() {

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

    public LocalDateTime getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(LocalDateTime projectDate) {
        this.projectDate = projectDate;
    }
}

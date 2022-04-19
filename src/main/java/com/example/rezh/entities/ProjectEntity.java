package com.example.rezh.entities;


import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "LONGTEXT")
    private String files;

    @DateTimeFormat()
    private LocalDateTime projectDate = LocalDateTime.now();



    public ProjectEntity() {

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

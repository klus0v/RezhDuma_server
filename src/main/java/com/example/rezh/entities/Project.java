package com.example.rezh.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(
            mappedBy = "projects",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AllFiles> files = new ArrayList<AllFiles>();

    @DateTimeFormat()
    private LocalDateTime projectDate = LocalDateTime.now();

    public void addFile(AllFiles file) {
        files.add(file);
        file.setProjects(this);
    }

    public void removeFile(AllFiles file) {
        files.remove(file);
        file.setProjects(null);
    }

}

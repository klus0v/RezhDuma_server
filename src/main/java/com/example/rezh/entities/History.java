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
@Table(name = "stories")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @DateTimeFormat()
    private LocalDateTime historyDate = LocalDateTime.now();

    @OneToMany(
            mappedBy = "history",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<File> files = new ArrayList<File>();

    public void addFile(File file) {
        files.add(file);
        file.setHistory(this);
    }

    public void removeFile(File file) {
        files.remove(file);
        file.setHistory(null);
    }

}

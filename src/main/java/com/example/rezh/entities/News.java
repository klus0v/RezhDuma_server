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
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Boolean event;


    @DateTimeFormat()
    private LocalDateTime newsDate = LocalDateTime.now();

    @OneToMany(
            mappedBy = "news",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<File> files = new ArrayList<>();

    public void addFile(File file) {
        files.add(file);
        file.setNews(this);
    }

    public void removeFile(File file) {
        files.remove(file);
        file.setNews(null);
    }

}

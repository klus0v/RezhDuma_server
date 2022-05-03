package com.example.rezh.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;
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
    private List<AllFiles> files = new ArrayList<AllFiles>();

    public void addFile(AllFiles file) {
        files.add(file);
        file.setNews(this);
    }

    public void removeFile(AllFiles file) {
        files.remove(file);
        file.setNews(null);
    }

}

package com.example.rezh.entities;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appeals")
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String districtTag;

    private String topicTag;

    @DateTimeFormat()
    private LocalDateTime appealDate = LocalDateTime.now();

    private String responsibleName;

    @Column(columnDefinition = "TEXT")
    private String response;

    private Boolean frequent = false;

    @DateTimeFormat()
    private LocalDateTime responseDate;

    private Boolean answered = false;



    @OneToMany(
            mappedBy = "appeal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<File> files = new ArrayList<>();

    public void addFile(File file) {
        files.add(file);
        file.setAppeal(this);
    }

    public void removeFile(File file) {
        files.remove(file);
        file.setAppeal(null);
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

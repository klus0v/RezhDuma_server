package com.example.rezh.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "all_files")
public class AllFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project projects;

    @ManyToOne(fetch = FetchType.LAZY)
    private History history;

    @ManyToOne(fetch = FetchType.LAZY)
    private Document documents;

    public AllFiles(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllFiles)) return false;
        return id != null && id.equals(((AllFiles) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

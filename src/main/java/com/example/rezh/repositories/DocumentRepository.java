package com.example.rezh.repositories;


import com.example.rezh.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document getById(Long id);

    @Query("Select c from Document c order by c.id desc")
    List<Document> findAll();

    @Query("Select c from Document c where c.title like %:title% or c.text like %:text%")
    List<Document> findAllByTitleContainingOrTextContaining(String title, String text);
}
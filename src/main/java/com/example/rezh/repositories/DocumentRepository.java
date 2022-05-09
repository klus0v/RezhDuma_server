package com.example.rezh.repositories;


import com.example.rezh.entities.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document getById(Long id);

    @Query("Select c from Document c order by c.id desc")
    List<Document> findAll();

    @Query("Select c from Document c where c.title like %:title% or c.text like %:text%")
    List<Document> findAllByTitleContainingOrTextContaining(String title, String text);
}
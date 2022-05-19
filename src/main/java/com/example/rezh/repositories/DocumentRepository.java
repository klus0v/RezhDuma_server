package com.example.rezh.repositories;


import com.example.rezh.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document getById(Long id);

    @Query("Select c from Document c order by c.id desc")
    List<Document> findAll();

    @Query(" SELECT c FROM Document c WHERE c.title like :text OR c.text like :text ")
    List<Document> findDocuments(@Param("text") String text);
}
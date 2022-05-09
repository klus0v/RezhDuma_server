package com.example.rezh.repositories;


import com.example.rezh.entities.Document;
import com.example.rezh.entities.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {
    History getById(Long id);

    @Query("Select c from History c where c.title like %:title% or c.text like %:text%")
    List<History> findAllByTitleContainingOrTextContaining(String title, String text);

    @Query("Select c from History c order by c.id desc")
    List<History> findAll();
}
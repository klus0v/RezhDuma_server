package com.example.rezh.repositories;


import com.example.rezh.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    History getById(Long id);

    @Query("Select c from History c where c.title like %:title% or c.text like %:text%")
    List<History> findAllByTitleContainingOrTextContaining(String title, String text);

    @Query("Select c from History c order by c.id desc")
    List<History> findAll();
}
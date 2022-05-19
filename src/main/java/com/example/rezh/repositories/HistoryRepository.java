package com.example.rezh.repositories;


import com.example.rezh.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    History getById(Long id);

    @Query(" SELECT c FROM History c WHERE c.title like :text OR c.text like :text ")
    List<History> findHistory(@Param("text") String text);

    @Query("Select c from History c order by c.id desc")
    List<History> findAll();
}
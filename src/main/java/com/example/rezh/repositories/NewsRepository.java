package com.example.rezh.repositories;


import com.example.rezh.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("Select c from News c where c.event = true order by c.id desc")
    List<News> findAllByEvent();

    @Query("Select c from News c order by c.id desc")
    List<News> findAll();

    News getById(Long id);

    @Query(" SELECT c FROM News c WHERE c.title like :text OR c.text like :text ")
    List<News> findNews(@Param("text") String text);

}
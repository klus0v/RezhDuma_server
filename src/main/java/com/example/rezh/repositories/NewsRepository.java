package com.example.rezh.repositories;


import com.example.rezh.entities.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
    Iterable<News> findAllByEvent(Boolean True);
    News getById(Long id);
}
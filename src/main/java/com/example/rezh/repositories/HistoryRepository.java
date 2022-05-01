package com.example.rezh.repositories;


import com.example.rezh.entities.History;
import com.example.rezh.entities.News;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<History, Long> {
    History getById(Long id);
}
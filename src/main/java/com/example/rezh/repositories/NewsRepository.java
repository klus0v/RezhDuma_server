package com.example.rezh.repositories;

import com.example.rezh.entities.NewsEntity;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<NewsEntity, Integer> {

}
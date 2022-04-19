package com.example.rezh.repositories;

import com.example.rezh.entities.DocumentEntity;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<DocumentEntity, Integer> {

}
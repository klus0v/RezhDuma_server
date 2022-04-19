package com.example.rezh.repositories;

import com.example.rezh.entities.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer> {

}
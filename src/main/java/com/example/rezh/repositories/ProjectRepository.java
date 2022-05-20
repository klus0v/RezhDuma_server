package com.example.rezh.repositories;


import com.example.rezh.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getById(Long id);

    @Query(" SELECT c FROM Project c WHERE c.title like :text OR c.text like :text order by c.id desc ")
    List<Project> findProjects(@Param("text") String text);

    @Query("Select c from Project c order by c.id desc")
    List<Project> findAll();
}
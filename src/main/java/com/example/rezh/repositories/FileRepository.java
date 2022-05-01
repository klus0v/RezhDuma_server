package com.example.rezh.repositories;


import com.example.rezh.entities.AllFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<AllFiles, Long> {
}

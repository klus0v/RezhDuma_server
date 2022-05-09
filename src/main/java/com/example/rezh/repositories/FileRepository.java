package com.example.rezh.repositories;


import com.example.rezh.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}

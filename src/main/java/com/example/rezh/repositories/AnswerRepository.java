package com.example.rezh.repositories;


import com.example.rezh.entities.votes.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}

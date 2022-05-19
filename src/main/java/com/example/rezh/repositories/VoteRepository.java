package com.example.rezh.repositories;


import com.example.rezh.entities.votes.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllBySurvey(boolean survey);

    @Query("Select c from Vote c where c.topic like %:topic%")
    List<Vote> findAllByTopicContaining(String topic);
}

package com.example.rezh.repositories;


import com.example.rezh.entities.votes.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("Select c from Vote c WHERE c.isActive = true order by c.id desc")
    List<Vote> findAll();

    @Query(" SELECT c FROM Vote c WHERE c.isActive = true and c.topic like :text order by c.id desc ")
    List<Vote> findVotes(@Param("text") String text);
}

package com.example.rezh.repositories;


import com.example.rezh.entities.votes.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("Select c from Vote c order by c.id desc")
    List<Vote> findAll();

    @Query(" SELECT c FROM Vote c WHERE c.topic like :text order by c.id desc ")
    List<Vote> findVotes(@Param("text") String text);

    @Query(" SELECT c FROM Vote c WHERE c.topic like :text  order by c.id desc ")
    List<Vote> findVotesWithWord(@Param("text") String text);

}

package com.example.rezh.repositories;


import com.example.rezh.entities.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
    Appeal getById(Long id);

    List<Appeal> findAllByFrequent(Boolean True);

    List<Appeal> findAllByUserId(Long id);

    List<Appeal> findAllByUserIdAndAnswered(Long id, Boolean answered);

    List<Appeal> findAllByTypeAndTopicTagAndDistrictTag(String type, String topicTag, String districtTag);

    List<Appeal> findAllByTypeAndTopicTag(String type, String topicTag);

    List<Appeal> findAllByTypeAndDistrictTag(String type, String districtTag);

    List<Appeal> findAllByTopicTagAndDistrictTag(String topicTag, String districtTag);

    List<Appeal> findAllByType(String type);

    List<Appeal> findAllByTopicTag(String topicTag);

    List<Appeal> findAllByDistrictTag(String districtTag);




    @Query("Select c from Appeal c where c.text like %:text% and c.frequent = true or c.response like %:response% and c.frequent = true")
    List<Appeal> findAllByTextContainingOrResponseContaining(String text, String response);
}

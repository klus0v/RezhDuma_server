package com.example.rezh.repositories;


import com.amazonaws.services.dynamodbv2.xspec.B;
import com.example.rezh.entities.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
    Appeal getById(Long id);

    @Query(" SELECT c FROM Appeal c WHERE c.text like :text AND c.type like :type AND c.districtTag like :district AND c.topicTag like :topic AND c.frequent = true OR c.response like :response AND c.type like :type AND c.districtTag like :district AND c.topicTag like :topic AND c.frequent = true order by c.id desc ")
    List<Appeal> findTopAppeals(@Param("text") String text, @Param("response") String response, @Param("type") String type, @Param("district") String district, @Param("topic") String topic);


    @Query(" SELECT c FROM Appeal c WHERE c.user.id = :id AND c.text like :text AND c.type like :type AND c.districtTag like :district AND c.topicTag like :topic order by c.id desc ")
    List<Appeal> findUserAppeals(@Param("id") Long id, @Param("text") String text, @Param("type") String type, @Param("district") String district, @Param("topic") String topic);

    @Query(" SELECT c FROM Appeal c WHERE c.user.id = :id AND c.text like :text AND c.type like :type AND c.districtTag like :district AND c.topicTag like :topic AND c.answered = :answered order by c.id desc ")
    List<Appeal> findUserAppealsAnswered(@Param("id") Long id, @Param("text") String text, @Param("answered") Boolean answered,  @Param("type") String type, @Param("district") String district, @Param("topic") String topic);


    @Query(" SELECT c FROM Appeal c WHERE  c.text like :text AND c.type like :type AND c.districtTag like :district AND c.topicTag like :topic AND c.answered = :answered order by c.id desc ")
    List<Appeal> findAllAppealsAnswered(@Param("text") String text,@Param("answered") Boolean answered, @Param("type") String type, @Param("district") String district, @Param("topic") String topic);

    @Query(" SELECT c FROM Appeal c WHERE c.text like :text AND c.type like :type AND c.districtTag like :district AND c.topicTag like :topic order by c.id desc ")
    List<Appeal> findAllAppeals(@Param("text") String text, @Param("type") String type, @Param("district") String district, @Param("topic") String topic);
}

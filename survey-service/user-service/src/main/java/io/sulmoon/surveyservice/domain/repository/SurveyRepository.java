package io.sulmoon.surveyservice.domain.repository;

import io.sulmoon.surveyservice.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Long deleteSurveyById(Long surveyId);

    List<Survey> findAllByUserId(Long userId);

    @Query("select distinct s from Survey s join fetch s.answerList answer where answer.userId = :userId")
    List<Survey> findAllAnswersByUserId(@Param("userId") Long userId);
}

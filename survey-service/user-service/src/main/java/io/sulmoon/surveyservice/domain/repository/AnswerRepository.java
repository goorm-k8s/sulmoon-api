package io.sulmoon.surveyservice.domain.repository;

import io.sulmoon.surveyservice.domain.Answer;
import io.sulmoon.surveyservice.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Long deleteAnswerById(Long answerId);

    List<Answer> findAllByQuestion(Question question);

}

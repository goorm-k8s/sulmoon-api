package io.sulmoon.surveyservice.domain.repository;

import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Long deleteQuestionById(Long questionId);

    List<Question> findAllBySurvey(Survey survey);
}

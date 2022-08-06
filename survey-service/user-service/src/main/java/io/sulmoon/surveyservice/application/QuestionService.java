package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.dto.QuestionDto;

import java.util.Collection;
import java.util.List;

public interface QuestionService {

    Question createQuestion(Long surveyId, QuestionDto questionDto);

    Question searchQuestion(Long questionId);

    Long deleteQuestion(Long questionId);

    Question updateQuestion(QuestionDto questionDto);

    List<Question> searchQuestionListBySurvey(Long surveyId);

}

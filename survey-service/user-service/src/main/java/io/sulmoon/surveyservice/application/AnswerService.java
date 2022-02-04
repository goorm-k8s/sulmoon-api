package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Answer;
import io.sulmoon.surveyservice.dto.AnswerDto;

import java.util.List;

public interface AnswerService {

    Answer createAnswer(Long surveyId, Long questionId, AnswerDto answerDto);

    Answer searchAnswer(Long answerId);

    Long deleteAnswer(Long answerId);

    Answer updateAnswer(AnswerDto answerDto);

    List<Answer> searchAnswerListByQuestion(Long questionId);

}

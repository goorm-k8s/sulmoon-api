package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Answer;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.domain.repository.AnswerRepository;
import io.sulmoon.surveyservice.domain.repository.QuestionRepository;
import io.sulmoon.surveyservice.domain.repository.SurveyRepository;
import io.sulmoon.surveyservice.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService{

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    @Transactional
    public Answer createAnswer(Long surveyId,
                               Long questionId, AnswerDto answerDto) {
        Survey survey = surveyRepository.getById(surveyId);
        Question question = questionRepository.getById(questionId);
        Answer answer = Answer.builder()
                .answerContent(answerDto.getAnswerContent())
                .survey(survey)
                .question(question)
                .userId(answerDto.getUserId())
                .build();
        answer.setCreator(answerDto.getUserId()+"");
        answer.setModifier(answerDto.getUserId()+"");
        answer.setCreated(LocalDateTime.now());
        answer.setModified(LocalDateTime.now());
        return answerRepository.save(answer);
    }

    @Override
    public Answer searchAnswer(Long answerId) {
        return answerRepository.getById(answerId);
    }

    @Override
    @Transactional
    public Long deleteAnswer(Long answerId) {
        return answerRepository.deleteAnswerById(answerId);
    }

    @Override
    @Transactional
    public Answer updateAnswer(AnswerDto answerDto) {
        Answer answer = answerRepository.getById(answerDto.getId());
        answer.setAnswerContent(answerDto.getAnswerContent());
        answer.setModifier(answerDto.getUserId()+"");
        answer.setModified(LocalDateTime.now());
        return answer;
    }

    @Override
    public List<Answer> searchAnswerListByQuestion(Long questionId) {
        Question question = questionRepository.getById(questionId);
        return answerRepository.findAllByQuestion(question);
    }
}

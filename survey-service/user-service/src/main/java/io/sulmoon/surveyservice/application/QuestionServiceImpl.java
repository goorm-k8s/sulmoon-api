package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.domain.repository.QuestionRepository;
import io.sulmoon.surveyservice.domain.repository.SurveyRepository;
import io.sulmoon.surveyservice.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;

    @Override
    @Transactional
    public Question createQuestion(Long surveyId, QuestionDto questionDto) {
        Survey survey = surveyRepository.getById(surveyId);
        Question question = Question.builder()
                .questionContent(questionDto.getQuestionContent())
                .multipleSelectionYn(questionDto.getMultipleSelectionYn())
                .subjectiveYn(questionDto.getSubjectiveYn())
                .survey(survey)
                .build();;
        question.setCreator(questionDto.getUserid()+"");
        question.setModifier(questionDto.getUserid()+"");
        question.setCreated(LocalDateTime.now());
        question.setModified(LocalDateTime.now());
        return questionRepository.save(question);
    }

    @Override
    public Question searchQuestion(Long questionId) {
        return questionRepository.getById(questionId);
    }

    @Override
    @Transactional
    public Long deleteQuestion(Long questionId) {
        return questionRepository.deleteQuestionById(questionId);
    }

    @Override
    @Transactional
    public Question updateQuestion(QuestionDto questionDto) {
        Question question = questionRepository.getById(questionDto.getId());
        question.setQuestionContent(questionDto.getQuestionContent());
        question.setSubjectiveYn(questionDto.getSubjectiveYn());
        question.setMultipleSelectionYn(questionDto.getMultipleSelectionYn());
        question.setModifier(questionDto.getUserid()+"");
        question.setModified(LocalDateTime.now());
        return question;
    }

    @Override
    public List<Question> searchQuestionListBySurvey(Long surveyId) {
        Survey survey = surveyRepository.getById(surveyId);
        return questionRepository.findAllBySurvey(survey);
    }
}

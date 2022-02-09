package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.domain.repository.QuestionRepository;
import io.sulmoon.surveyservice.domain.repository.SurveyRepository;
import io.sulmoon.surveyservice.dto.QuestionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


class QuestionServiceImplTest {

    private final QuestionRepository mockQuestionRepository = mock(QuestionRepository.class);
    private final SurveyRepository mockSurveyRepository = mock(SurveyRepository.class);
    private final QuestionService questionService = new QuestionServiceImpl(mockQuestionRepository, mockSurveyRepository);

    Long userId;
    Long questionId;
    String questionContent;
    Boolean subjectiveYn;
    Boolean multipleSelectionYn;
    String creator;
    String modifier;
    LocalDateTime created;
    LocalDateTime modified;
    Long surveyId;
    Survey survey;
    Question question1;
    Question question2;
    List<Question> questions;

    @BeforeEach
    void setUp() {
        userId = 1L;
        questionId = 1L;
        questionContent = "Q1";
        subjectiveYn = true;
        multipleSelectionYn = false;
        creator = "tempCreator";
        modifier = "tempCreator";
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
        surveyId = 1L;
        survey = Survey.builder()
                .id(surveyId)
                .title("제목")
                .description("설명")
                .userId(1L)
                .build();
        question1 = Question.builder()
                .id(1L)
                .questionContent(questionContent)
                .subjectiveYn(subjectiveYn)
                .multipleSelectionYn(multipleSelectionYn)
                .survey(survey)
                .build();
        question2 = Question.builder()
                .id(2L)
                .questionContent("Q2")
                .subjectiveYn(subjectiveYn)
                .multipleSelectionYn(multipleSelectionYn)
                .survey(survey)
                .build();
        questions = Arrays.asList(question1, question2);
    }

    @Test
    @DisplayName("질문 조회")
    void 질문_조회() {
        given(mockQuestionRepository.getById(questionId)).willReturn(question1);

        Question result = this.questionService.searchQuestion(1L);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getQuestionContent()).isEqualTo(questionContent),
                () -> assertThat(result.getSubjectiveYn()).isEqualTo(subjectiveYn),
                () -> assertThat(result.getMultipleSelectionYn()).isEqualTo(multipleSelectionYn),
                () -> assertThat(result.getSurvey().getId()).isEqualTo(surveyId)
        );
    }

    @Test
    @DisplayName("질문 삭제")
    void 질문_삭제() {
        given(mockQuestionRepository.deleteQuestionById(questionId)).willReturn(1L);

        Long result = this.questionService.deleteQuestion(questionId);

        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("질문 수정")
    void 질문_수정() {

        QuestionDto updatedQuestionDto = QuestionDto.builder()
                .id(questionId)
                .questionContent("수정된 질문")
                .subjectiveYn(false)
                .multipleSelectionYn(true)
                .creator(creator)
                .modifier("수정자")
                .created(created)
                .modified(LocalDateTime.now())
                .build();

        given(mockQuestionRepository.getById(questionId)).willReturn(question1);

        Question result = this.questionService.updateQuestion(updatedQuestionDto);

        assertAll(
                () -> assertThat(result.getQuestionContent()).isEqualTo(updatedQuestionDto.getQuestionContent()),
                () -> assertThat(result.getSubjectiveYn()).isEqualTo(updatedQuestionDto.getSubjectiveYn()),
                () -> assertThat(result.getMultipleSelectionYn()).isEqualTo(updatedQuestionDto.getMultipleSelectionYn()),
                () -> assertThat(result.getModifier()).isEqualTo(updatedQuestionDto.getModifier()),
                () -> assertThat(result.getModified()).isEqualTo(updatedQuestionDto.getModified()),
                () -> assertThat(result.getSurvey().getId()).isEqualTo(surveyId)
        );
    }

    @Test
    @DisplayName("질문 생성")
    void 질문_생성() {
        Question givenQuestion = Question.builder()
                .questionContent(questionContent)
                .subjectiveYn(subjectiveYn)
                .multipleSelectionYn(multipleSelectionYn)
                .survey(survey)
                .build();

        given(mockQuestionRepository.save(givenQuestion)).willReturn(question1);

        Question result = this.questionService.createQuestion(surveyId, QuestionDto.builder()
                .questionContent(questionContent)
                .subjectiveYn(subjectiveYn)
                .multipleSelectionYn(multipleSelectionYn)
                .survey(survey)
                .build()
        );

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getQuestionContent()).isEqualTo(questionContent),
                () -> assertThat(result.getSubjectiveYn()).isEqualTo(subjectiveYn),
                () -> assertThat(result.getMultipleSelectionYn()).isEqualTo(multipleSelectionYn),
                () -> assertThat(result.getSurvey().getId()).isEqualTo(surveyId)
        );

    }

    @Test
    @DisplayName("설문 식별자로 질문 목록 조회")
    void 질문_목록_조회() {
        given(mockSurveyRepository.getById(surveyId)).willReturn(survey);
        given(mockQuestionRepository.findAllBySurvey(survey)).willReturn(questions);

        List<Question> results = this.questionService.searchQuestionListBySurvey(surveyId);

        assertAll(
                () -> assertThat(results.get(0).getId()).isEqualTo(1L),
                () -> assertThat(results.get(1).getId()).isEqualTo(2L),
                () -> assertThat(results.get(0).getSurvey().getId()).isEqualTo(1L),
                () -> assertThat(results.get(1).getSurvey().getId()).isEqualTo(1L)
        );
    }
}
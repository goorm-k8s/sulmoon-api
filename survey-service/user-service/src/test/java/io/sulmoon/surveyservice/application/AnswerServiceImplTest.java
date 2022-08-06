package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Answer;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.domain.repository.AnswerRepository;
import io.sulmoon.surveyservice.domain.repository.QuestionRepository;
import io.sulmoon.surveyservice.domain.repository.SurveyRepository;
import io.sulmoon.surveyservice.dto.AnswerDto;
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

class AnswerServiceImplTest {

    private final SurveyRepository mockSurveyRepository = mock(SurveyRepository.class);
    private final QuestionRepository mockQuestionRepository = mock(QuestionRepository.class);
    private final AnswerRepository mockAnswerRepository = mock(AnswerRepository.class);
    private final AnswerService answerService = new AnswerServiceImpl(
            mockSurveyRepository, mockQuestionRepository, mockAnswerRepository);

    Long userId;
    Long surveyId;
    Long questionId;
    Long answerId;
    String answerContent;
    String creator;
    String modifier;
    LocalDateTime created;
    LocalDateTime modified;

    Survey survey = Survey.builder()
            .id(1L)
            .build();

    Question question = Question.builder()
            .id(1L)
            .build();

    Answer answer1;
    Answer answer2;
    List<Answer> answers;

    @BeforeEach
    void setUp() {
        userId = 1L;
        surveyId = 1L;
        questionId = 1L;
        answerId = 1L;
        answerContent = "답";
        creator = "tempCreator";
        modifier = "tempCreator";
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
        answer1 = Answer.builder()
                .id(1L)
                .answerContent(answerContent)
                .survey(survey)
                .question(question)
                .userId(userId)
                .build();
        answer2 = Answer.builder()
                .id(2L)
                .answerContent(answerContent)
                .survey(survey)
                .question(question)
                .userId(userId)
                .build();
        answers = Arrays.asList(answer1, answer2);
    }

    @Test
    @DisplayName("답변 생성")
    void 답변_생성() {
        Answer givenAnswer = Answer.builder()
                .answerContent(answerContent)
                .survey(survey)
                .question(question)
                .userId(userId)
                .build();

        given(mockAnswerRepository.save(givenAnswer)).willReturn(answer1);

        Answer result = this.answerService.createAnswer(
                surveyId, questionId,
                AnswerDto.builder()
                .answerContent(answerContent)
                .userId(userId)
                .build()
        );

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getAnswerContent()).isEqualTo(answerContent)
        );
    }

    @Test
    @DisplayName("답변 조회")
    void 답변_조회() {
        given(mockAnswerRepository.getById(answerId)).willReturn(answer1);

        Answer result = this.answerService.searchAnswer(answerId);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getAnswerContent()).isEqualTo(answerContent)
        );
    }

    @Test
    @DisplayName("답변 삭제")
    void 답변_삭제() {
        given(mockAnswerRepository.deleteAnswerById(answerId)).willReturn(1L);

        Long result = this.answerService.deleteAnswer(answerId);

        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("답변 수정")
    void 답변_수정() {
        AnswerDto updatedAnswerDto = AnswerDto.builder()
                .id(answerId)
                .answerContent("수정된 답변")
                .build();

        given(mockAnswerRepository.getById(answerId)).willReturn(answer1);

        Answer result = this.answerService.updateAnswer(updatedAnswerDto);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(updatedAnswerDto.getId()),
                () -> assertThat(result.getAnswerContent()).isEqualTo(updatedAnswerDto.getAnswerContent())
        );
    }

    @Test
    @DisplayName("질문 식별자로 답변 목록 조회")
    void 답변_목록() {
        given(mockQuestionRepository.getById(questionId)).willReturn(question);
        given(mockAnswerRepository.findAllByQuestion(question)).willReturn(answers);

        List<Answer> results = answerService.searchAnswerListByQuestion(questionId);

        assertAll(
                () -> assertThat(results.get(0).getId()).isEqualTo(1),
                () -> assertThat(results.get(1).getId()).isEqualTo(2)
        );

    }
}
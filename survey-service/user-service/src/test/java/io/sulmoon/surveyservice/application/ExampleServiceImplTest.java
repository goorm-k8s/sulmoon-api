package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Example;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.repository.ExampleRepository;
import io.sulmoon.surveyservice.domain.repository.QuestionRepository;
import io.sulmoon.surveyservice.dto.ExampleDto;
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

class ExampleServiceImplTest {

    private final ExampleRepository mockExampleRepository = mock(ExampleRepository.class);
    private final QuestionRepository mockQuestionRepository = mock(QuestionRepository.class);
    private final ExampleService exampleService = new ExampleServiceImpl(mockExampleRepository, mockQuestionRepository);

    String exampleContent;
    String creator;
    String modifier;
    LocalDateTime created;
    LocalDateTime modified;
    Example example1;
    Example example2;
    Long questionId;
    Question question;
    List<Example> examples;

    @BeforeEach
    void setUp() {
        exampleContent = "A1";
        creator = "tempCreator";
        modifier = "tempCreator";
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
        questionId = 1L;
        question = Question.builder()
                .id(questionId)
                .questionContent("Q1")
                .subjectiveYn(true)
                .multipleSelectionYn(false)
                .build();
        example1 = Example.builder()
                .id(1L)
                .exampleContent(exampleContent)
                .question(question)
                .build();
        example2 = Example.builder()
                .id(2L)
                .exampleContent(exampleContent)
                .question(question)
                .build();
        examples = Arrays.asList(example1, example2);
    }

    @Test
    @DisplayName("보기 생성")
    void 보기_생성() {
        Example givenExample = Example.builder()
                .exampleContent(exampleContent)
                .build();

        given(mockQuestionRepository.getById(questionId)).willReturn(question);
        given(mockExampleRepository.save(givenExample)).willReturn(example1);

        Example result = this.exampleService.createExample(questionId, ExampleDto.builder()
                .exampleContent(exampleContent)
                .build()
        );

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getExampleContent()).isEqualTo(exampleContent),
                () -> assertThat(result.getQuestion().getId()).isEqualTo(questionId)
        );

    }

    @Test
    @DisplayName("보기 조회")
    void 보기_조회() {
        given(mockExampleRepository.getById(1L)).willReturn(example1);

        Example result = this.exampleService.searchExample(1L);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getExampleContent()).isEqualTo(exampleContent),
                () -> assertThat(result.getQuestion().getId()).isEqualTo(questionId)
        );
    }

    @Test
    @DisplayName("보기 삭제")
    void 보기_삭제() {
        given(mockExampleRepository.deleteExampleById(1L)).willReturn(1L);

        Long result = this.exampleService.deleteExample(1L);

        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("보기 수정")
    void 보기_수정() {
        ExampleDto updatedExampleDto = ExampleDto.builder()
                .id(1L)
                .exampleContent("수정된 보기")
                .build();
        given(mockExampleRepository.getById(1L)).willReturn(example1);

        Example result = this.exampleService.updateExample(updatedExampleDto);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(updatedExampleDto.getId()),
                () -> assertThat(result.getExampleContent()).isEqualTo(updatedExampleDto.getExampleContent()),
                () -> assertThat(result.getQuestion().getId()).isEqualTo(questionId)
        );

    }

    @Test
    @DisplayName("질문 식별자로 보기 목록 조회")
    void 보기_목록_조회() {
        given(mockQuestionRepository.getById(questionId)).willReturn(question);
        given(mockExampleRepository.findAllByQuestion(question)).willReturn(examples);

        List<Example> results = this.exampleService.searchExampleListByQuestion(questionId);

        assertAll(
                () -> assertThat(results.get(0).getId()).isEqualTo(1L),
                () -> assertThat(results.get(1).getId()).isEqualTo(2L),
                () -> assertThat(results.get(0).getQuestion().getId()).isEqualTo(1L),
                () -> assertThat(results.get(1).getQuestion().getId()).isEqualTo(1L)
        );

    }
}
package io.sulmoon.surveyservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    @DisplayName("질문은 질문내용, 주관식여부, 다중선택여부를 가져야한다.")
    void 질문_도메인_구성() {
        //given
        String questionContent = "Q1";
        Boolean subjectiveYn = true;
        Boolean multipleSelectionYn = false;

        //when
        Question question = Question.builder()
                .questionContent(questionContent)
                .subjectiveYn(subjectiveYn)
                .multipleSelectionYn(multipleSelectionYn)
                .build();

        //then
        assertAll(
                () -> assertThat(question.getQuestionContent()).isEqualTo(questionContent),
                () -> assertThat(question.getSubjectiveYn()).isEqualTo(subjectiveYn),
                () -> assertThat(question.getMultipleSelectionYn()).isEqualTo(multipleSelectionYn)
        );
    }
}
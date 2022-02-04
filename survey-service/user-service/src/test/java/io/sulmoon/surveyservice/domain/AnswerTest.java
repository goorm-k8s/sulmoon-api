package io.sulmoon.surveyservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    @DisplayName("답변은 답변 내용을 가져야 한다.")
    void 답변_도메인_구성() {
        //given
        String answerContent = "답변";

        //when
        Answer answer = Answer.builder()
                .answerContent(answerContent)
                .build();

        //then
        assertThat(answer.getAnswerContent()).isEqualTo(answerContent);
    }
}

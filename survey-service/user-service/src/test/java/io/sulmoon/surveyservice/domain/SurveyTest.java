package io.sulmoon.surveyservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SurveyTest {

    @Test
    @DisplayName("설문은 설문제목, 설문설명를 가진다")
    void 설문_도메인_구성() {
        //given
        String title = "제목";
        String description = "설명";

        //when
        Survey survey = Survey.builder()
                .title(title)
                .description(description)
                .build();

        //then
        assertAll(
                () -> assertThat(survey.getTitle()).isEqualTo(title),
                () -> assertThat(survey.getDescription()).isEqualTo(description)
        );

    }

}
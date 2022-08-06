package io.sulmoon.surveyservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {

    @Test
    @DisplayName("보기은 보기내용을 가져야한다.")
    void 보기_도메인_구성() {
        //given
        String exampleContent = "E1";

        //when
        Example example = Example.builder()
                .exampleContent(exampleContent)
                .build();

        //then
        assertThat(example.getExampleContent()).isEqualTo(exampleContent);
    }


}
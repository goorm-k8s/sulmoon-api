package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionExamplesInfoResponseDto {
    private final Long questionId;
    private final String questionContent;
    private final Boolean subjectiveYn;
    private final Boolean multipleSelectionYn;
    private List<ExamplesInfoResponseDto> examples;

    public void setExamples(List<ExamplesInfoResponseDto> examples) {
        this.examples = examples;
    }
}

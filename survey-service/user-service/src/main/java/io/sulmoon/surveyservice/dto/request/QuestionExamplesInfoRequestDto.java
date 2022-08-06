package io.sulmoon.surveyservice.dto.request;

import io.sulmoon.surveyservice.dto.response.ExamplesInfoResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionExamplesInfoRequestDto {
    private final Long questionId;
    private final String questionContent;
    private final Boolean subjectiveYn;
    private final Boolean multipleSelectionYn;
    private List<ExamplesInfoRequestDto> examples;

    public void setExamples(List<ExamplesInfoRequestDto> examples) {
        this.examples = examples;
    }
}

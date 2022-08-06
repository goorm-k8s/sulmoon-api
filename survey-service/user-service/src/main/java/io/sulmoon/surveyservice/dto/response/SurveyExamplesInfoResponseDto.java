package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class SurveyExamplesInfoResponseDto {
    private final Long surveyId;
    private final String title;
    private final String description;
    private List<QuestionExamplesInfoResponseDto> questions;

    public void setQuestions(List<QuestionExamplesInfoResponseDto> questions) {
        this.questions = questions;
    }

}

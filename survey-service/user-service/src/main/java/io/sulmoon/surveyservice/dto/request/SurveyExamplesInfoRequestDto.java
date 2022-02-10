package io.sulmoon.surveyservice.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class SurveyExamplesInfoRequestDto {
    private final Long surveyId;
    private final String title;
    private final String description;
//    private List<QuestionExamplesInfoRequestDto> questions;
//
//    public void setQuestions(List<QuestionExamplesInfoRequestDto> questions) {
//        this.questions = questions;
//    }

}

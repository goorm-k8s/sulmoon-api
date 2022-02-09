package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionAnswersInfoResponseDto {
    private final Long questionId;
    private final String questionContent;
    private final Boolean subjectiveYn;
    private final Boolean multipleSelectionYn;
    private List<AnswersInfoResponseDto> answers;

    public void setAnswers(List<AnswersInfoResponseDto> answers) {
        this.answers = answers;
    }
}

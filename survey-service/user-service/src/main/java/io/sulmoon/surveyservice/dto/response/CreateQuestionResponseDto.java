package io.sulmoon.surveyservice.dto.response;

import io.sulmoon.surveyservice.domain.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateQuestionResponseDto {

    private final Long id;
    private final String questionContent;
    private final Boolean subjectiveYn;
    private final Boolean multipleSelectionYn;
    private final Long surveyId;
}

package io.sulmoon.surveyservice.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UpdateQuestionRequestDto {
    private final String questionContent;
    private final Boolean subjectiveYn;
    private final Boolean multipleSelectionYn;
}

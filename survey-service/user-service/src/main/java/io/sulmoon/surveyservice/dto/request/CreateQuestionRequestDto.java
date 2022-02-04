package io.sulmoon.surveyservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class CreateQuestionRequestDto {

    private final String questionContent;
    private final Boolean subjectiveYn;
    private final Boolean multipleSelectionYn;
}

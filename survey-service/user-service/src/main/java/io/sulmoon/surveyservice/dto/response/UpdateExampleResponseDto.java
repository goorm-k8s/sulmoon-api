package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateExampleResponseDto {
    private final Long id;
    private final String exampleContent;
    private final Long questionId;
}

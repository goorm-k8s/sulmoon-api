package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExamplesInfoResponseDto {
    private final Long exampleId;
    private final String exampleContent;
}

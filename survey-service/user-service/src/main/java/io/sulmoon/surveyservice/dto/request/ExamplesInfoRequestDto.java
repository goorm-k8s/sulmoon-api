package io.sulmoon.surveyservice.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExamplesInfoRequestDto {
    private final Long exampleId;
    private final String exampleContent;
}

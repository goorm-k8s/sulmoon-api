package io.sulmoon.surveyservice.dto.response.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchExampleResponseDto {
    private final Long id;
    private final String exampleContent;
    private final Long questionId;
}

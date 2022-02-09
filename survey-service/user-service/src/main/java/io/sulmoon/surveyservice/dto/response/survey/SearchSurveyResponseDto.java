package io.sulmoon.surveyservice.dto.response.survey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchSurveyResponseDto {

    private final Long id;
    private final String title;
    private final String description;
    private final Long userId;
}

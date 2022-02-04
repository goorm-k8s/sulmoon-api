package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateSurveyResponseDto {

    private final Long id;
    private final String title;
    private final String description;
    private final Long userId;
}

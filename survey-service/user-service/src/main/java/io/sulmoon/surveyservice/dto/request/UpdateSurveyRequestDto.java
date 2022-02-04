package io.sulmoon.surveyservice.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@Builder
public class UpdateSurveyRequestDto {
    private final String title;
    private final String description;

}

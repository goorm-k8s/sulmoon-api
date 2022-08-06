package io.sulmoon.surveyservice.dto.response.example;

import io.sulmoon.surveyservice.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateExampleResponseDto {
    private final Long id;
    private final String exampleContent;
    private final Long questionId;
}

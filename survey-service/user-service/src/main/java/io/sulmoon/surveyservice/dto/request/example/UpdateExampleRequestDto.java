package io.sulmoon.surveyservice.dto.request.example;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateExampleRequestDto {
    private String exampleContent;
    private Long questionId;
}

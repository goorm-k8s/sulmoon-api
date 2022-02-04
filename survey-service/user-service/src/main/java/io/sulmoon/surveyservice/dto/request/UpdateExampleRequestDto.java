package io.sulmoon.surveyservice.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateExampleRequestDto {
    private String exampleContent;
}

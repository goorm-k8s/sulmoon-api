package io.sulmoon.surveyservice.dto;

import io.sulmoon.surveyservice.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExampleDto {
    private Long id;
    private String exampleContent;
    private Question question;
}

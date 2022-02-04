package io.sulmoon.surveyservice.dto;

import io.sulmoon.surveyservice.domain.Survey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class QuestionDto {

    private Long id;

    // private Survey survey;

    private String questionContent;
    private Boolean subjectiveYn;
    private Boolean multipleSelectionYn;
    private Survey survey;
    private String creator;
    private String modifier;
    private LocalDateTime created;
    private LocalDateTime modified;
}

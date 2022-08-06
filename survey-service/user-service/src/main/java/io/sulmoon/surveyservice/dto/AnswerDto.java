package io.sulmoon.surveyservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AnswerDto {

    private Long id;
    private String answerContent;
    private String creator;
    private String modifier;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Long userId;
    private Long surveyId;
    private Long questionId;
}

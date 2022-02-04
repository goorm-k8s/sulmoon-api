package io.sulmoon.surveyservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SurveyDto {

    private Long id;

    // private User user;

    private String title;
    private String description;
    private String creator;
    private String modifier;
    private LocalDateTime created;
    private LocalDateTime modified;
}

package io.sulmoon.surveyservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String email;
    private String providerId;
    private UserRole role;
    private String creator;
    private String modifier;
    private LocalDateTime created;
    private LocalDateTime modified;
}

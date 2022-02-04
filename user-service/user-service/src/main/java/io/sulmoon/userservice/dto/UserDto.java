package io.sulmoon.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String provider;
    private String providerId;
}

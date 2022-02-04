package io.sulmoon.userservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateUserResponseDTO {
    private final Long id;
    private final String email;
    private final String providerId;
}

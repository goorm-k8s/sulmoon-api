package io.sulmoon.userservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtResponse {
    private final String token;
    private final String userId;
    private final String providerId;
}

package io.sulmoon.userservice.application;

import io.sulmoon.userservice.domain.User;
import io.sulmoon.userservice.dto.request.CreateUserRequestDTO;

public interface UserService {

    User createUser(CreateUserRequestDTO request);
    User searchUser(Long userId);
    Long deleteUser(Long userId);

    String OAuth2Login(String token, String provider);

    User searchUserByProviderId(String providerId);
}

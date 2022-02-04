package io.sulmoon.userservice.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void 사용자는_email과_provider와_providerId를_가진다() {
        // Given
        String email = "goorm@goorm.com";
        String providerId = "naver_2d34jk23";


        // When
        User user = User.builder()
            .email(email)
            .providerId(providerId)
            .role(UserRole.ROLE_USER)
            .build();

        // Then
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(email),
                () -> assertThat(user.getRole()).isSameAs(UserRole.ROLE_USER),
                () -> assertThat(user.getProviderId()).isEqualTo(providerId)
        );

    }
}

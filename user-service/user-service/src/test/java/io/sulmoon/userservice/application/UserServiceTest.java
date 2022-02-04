package io.sulmoon.userservice.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.sulmoon.userservice.application.provider.OAuth2;
import io.sulmoon.userservice.domain.User;
import io.sulmoon.userservice.domain.repository.UserRepository;
import io.sulmoon.userservice.dto.request.CreateUserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private final UserRepository mockUserRepository = mock(UserRepository.class);
    private final OAuth2 mockOAuth2 = mock(OAuth2.class);
    private final UserService userService = new UserServiceImpl(mockUserRepository, mockOAuth2);

    Long userId;
    String email;
    String providerId;
    User user;

    @BeforeEach
    void setUp() {
        userId = 1L;
        email = "goorm@goorm.com";
        providerId = "naver_2d34jk23";
        user =  User.builder()
                .id(1L)
                .email(email)
                .providerId(providerId)
                .build();
    }

    @Test
    @DisplayName("유저 생성")
    void 유저_생성() {
        User givenUser = User.builder().email(email).providerId(providerId).build();

        given(mockUserRepository.save(givenUser)).willReturn(user);

        User result = this.userService.createUser(new CreateUserRequestDTO(email, providerId));

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEmail()).isEqualTo(email),
                () -> assertThat(result.getProviderId()).isEqualTo(providerId)
        );
    }

    @Test
    @DisplayName("유저 조회")
    void 유저_조회() {
        given(mockUserRepository.getById(userId)).willReturn(user);

        User result = this.userService.searchUser(userId);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEmail()).isEqualTo(email),
                () -> assertThat(result.getProviderId()).isEqualTo(providerId)
        );
    }

    @Test
    @DisplayName("유저 삭제")
    void 유저_삭제() {
        given(mockUserRepository.deleteUserById(userId)).willReturn(1L);

        Long result = this.userService.deleteUser(userId);

        assertThat(result).isEqualTo(1L);
    }
}

package io.sulmoon.userservice.application;

import io.sulmoon.userservice.application.provider.OAuth2;
import io.sulmoon.userservice.application.provider.OAuth2UserInfo;
import io.sulmoon.userservice.domain.User;
import io.sulmoon.userservice.domain.UserRole;
import io.sulmoon.userservice.domain.repository.UserRepository;
import io.sulmoon.userservice.dto.request.CreateUserRequestDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final OAuth2 oAuth2;

    @Override
    @Transactional
    public User createUser(CreateUserRequestDTO request) {
        return this.userRepository.save(User.builder()
                .email(request.getEmail())
                .providerId(request.getProviderId())
                .role(UserRole.ROLE_USER).build());
    }

    @Override
    public User searchUser(Long userId) {
        return this.userRepository.getById(userId);
    }

    @Override
    @Transactional
    public Long deleteUser(Long userId) {
        return this.userRepository.deleteUserById(userId);
    }

    @Override
    @Transactional
    public String OAuth2Login(String token, String provider) {

        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        OAuth2UserInfo userInfo = oAuth2.getUserInfo(token, provider);
        String oAuth2Id = userInfo.getId();
        String email = userInfo.getEmail();

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User oAuth2User = userRepository.findByProviderId(oAuth2Id)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (oAuth2User == null) {
            oAuth2User = User.builder().email(email).role(UserRole.ROLE_USER).providerId(oAuth2Id).build();
            oAuth2User = userRepository.save(oAuth2User);
        }

        return oAuth2Id + " " + oAuth2User.getId();
    }

    @Override
    public User searchUserByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId).orElse(null);
    }
}

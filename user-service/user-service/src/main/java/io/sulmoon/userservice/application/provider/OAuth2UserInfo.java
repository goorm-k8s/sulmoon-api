package io.sulmoon.userservice.application.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2UserInfo {
    String id;
    String email;
}

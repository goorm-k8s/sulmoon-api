package io.sulmoon.userservice.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    String token;
    String provider;
}

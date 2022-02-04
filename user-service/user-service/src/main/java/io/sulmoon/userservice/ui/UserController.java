package io.sulmoon.userservice.ui;

import io.sulmoon.userservice.application.UserService;
import io.sulmoon.userservice.domain.User;
import io.sulmoon.userservice.dto.request.CreateUserRequestDTO;
import io.sulmoon.userservice.dto.request.TokenDto;
import io.sulmoon.userservice.dto.response.CreateUserResponseDTO;
import io.sulmoon.userservice.dto.response.DeleteUsersResponseDTO;
import io.sulmoon.userservice.dto.response.JwtResponse;
import io.sulmoon.userservice.dto.response.SearchUserResponseDTO;
import io.sulmoon.userservice.util.TokenRequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenRequestUtil tokenRequestUtil;

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO requestData) {
        User user = this.userService.createUser(requestData);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CreateUserResponseDTO(user.getId(), user.getEmail(), user.getProviderId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SearchUserResponseDTO> searchUser(
        @PathVariable Long userId) {
        User user = this.userService.searchUser(userId);
        SearchUserResponseDTO result = new SearchUserResponseDTO(user.getId(), user.getEmail(), user.getProviderId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteUsersResponseDTO> deleteUser(
            @PathVariable Long userId) {
        Long deletedUserId = this.userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteUsersResponseDTO(deletedUserId));
    }

    @PostMapping(value = "/login/oauth2")
    public ResponseEntity<?> createAuthenticationTokenByOAuth2(@RequestBody TokenDto TokenDto) throws Exception {
        String userIdAndProviderId = userService.OAuth2Login(TokenDto.getToken(), TokenDto.getProvider());
        String[] ids = userIdAndProviderId.split(" ");
        String providerId = ids[0];
        String userId = ids[1];
        String token = tokenRequestUtil.requestToken(providerId);
        return ResponseEntity.ok(new JwtResponse(token, userId, providerId));
    }

    @GetMapping(value = "/providerIds/{providerId}")
    public ResponseEntity<SearchUserResponseDTO> searchUserByProviderId(
            @PathVariable String providerId) {
        User user = userService.searchUserByProviderId(providerId);
        SearchUserResponseDTO result = new SearchUserResponseDTO(user.getId(), user.getEmail(), user.getProviderId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

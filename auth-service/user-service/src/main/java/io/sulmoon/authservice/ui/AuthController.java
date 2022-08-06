package io.sulmoon.authservice.ui;

import io.sulmoon.authservice.application.security.UserDetailsServiceImpl;
import io.sulmoon.authservice.domain.User;
import io.sulmoon.authservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping(value = "/auth/{providerId}")
    public ResponseEntity<String> getUser(
            @PathVariable String providerId) {
        Optional<User> user = userRepository.findByProviderId(providerId);
        userDetailsService.loadUserByUsername(user.get().getProviderId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(user.get().getProviderId());
    }

}

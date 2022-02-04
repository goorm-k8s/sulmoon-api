package io.sulmoon.authservice.domain.repository;

import io.sulmoon.authservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderId(String providerId);
}

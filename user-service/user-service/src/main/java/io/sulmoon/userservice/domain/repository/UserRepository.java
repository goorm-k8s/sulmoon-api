package io.sulmoon.userservice.domain.repository;

import io.sulmoon.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Long deleteUserById(Long userId);

    Optional<User> findByProviderId(String providerId);
}

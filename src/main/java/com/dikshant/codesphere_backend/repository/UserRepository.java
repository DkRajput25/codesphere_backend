package com.dikshant.codesphere_backend.repository;

import com.dikshant.codesphere_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username); // ✅ required for usernameExists()
}

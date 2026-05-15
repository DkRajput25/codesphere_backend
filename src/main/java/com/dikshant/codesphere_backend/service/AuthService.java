package com.dikshant.codesphere_backend.service;

import com.dikshant.codesphere_backend.model.User;
import com.dikshant.codesphere_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User register(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password)); // ✅ store encoded password
        return userRepository.save(user);
    }

    public boolean validateLogin(String username, String rawPassword) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) return false;

        User u = optUser.get();
        System.out.println("🔍 DB PasswordHash = " + u.getPasswordHash());

        // ✅ Check password using encoded value
        return encoder.matches(rawPassword, u.getPasswordHash());
    }
}

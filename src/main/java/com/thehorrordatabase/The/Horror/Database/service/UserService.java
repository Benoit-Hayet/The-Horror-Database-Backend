package com.thehorrordatabase.The.Horror.Database.service;


import com.thehorrordatabase.The.Horror.Database.model.User;
import com.thehorrordatabase.The.Horror.Database.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User registerUser(String lastname, String firstname, String email, String password, String username, String avatarUrl, Set<String> roles, LocalDate birthdate, LocalDate createdAt) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        User user = new User();
        user.setLastName(lastname);
        user.setFirstName(firstname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        user.setRoles(roles);
        user.setBirthdate(birthdate);
        user.setCreatedAt(createdAt);

        return userRepository.save(user);
    }
}

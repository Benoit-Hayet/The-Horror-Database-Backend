package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.dto.UserDTO;
import com.thehorrordatabase.The.Horror.Database.dto.UserLoginDTO;
import com.thehorrordatabase.The.Horror.Database.dto.UserRegistrationDTO;
import com.thehorrordatabase.The.Horror.Database.model.User;
import com.thehorrordatabase.The.Horror.Database.repository.UserRepository;
import com.thehorrordatabase.The.Horror.Database.security.AuthenticationService;
import com.thehorrordatabase.The.Horror.Database.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, AuthenticationService authenticationService, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile/{sub}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable String sub) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String currentUsername = authentication.getName(); // Nom d'utilisateur authentifié
        if (!sub.equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Accès interdit si ce n'est pas le profil de l'utilisateur authentifié
        }

        User user = userRepository.findByUsername(currentUsername).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDTO(user));
    }






    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User registeredUser = userService.registerUser(
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPassword(),
                Set.of("ROLE_USER"),
                userRegistrationDTO.getFirstName(),
                userRegistrationDTO.getLastName(),
                userRegistrationDTO.getBirthdate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authenticationService.authenticate(
                userLoginDTO.getEmail(),
                userLoginDTO.getPassword()
        );
        return ResponseEntity.ok(token);
    }
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        userDTO.setRole(user.getRoles());
        userDTO.setBirthdate(user.getBirthdate());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }
}
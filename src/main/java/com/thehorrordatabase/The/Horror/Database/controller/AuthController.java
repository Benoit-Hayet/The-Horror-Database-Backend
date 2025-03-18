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

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Vérifier si l'utilisateur est authentifié
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Pas d'utilisateur authentifié
        }

        // Récupérer l'id de l'utilisateur authentifié depuis le token JWT
        String currentUserIdStr = authentication.getPrincipal().toString();
        Long currentUserId;

        try {
            currentUserId = Long.parseLong(currentUserIdStr); // Convertir en Long
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Erreur dans le token
        }

        // Si l'id dans l'URL ne correspond pas à celui de l'utilisateur authentifié, refuser l'accès
        if (!id.equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Accès interdit
        }

        // Trouver l'utilisateur avec l'id
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(convertToDTO(user))) // Retourner les données sous forme de DTO
                .orElseGet(() -> ResponseEntity.notFound().build()); // Utilisateur non trouvé
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User registeredUser = userService.registerUser(
                userRegistrationDTO.getFirstName(),
                userRegistrationDTO.getLastName(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPassword(),
                userRegistrationDTO.getUsername(),
                userRegistrationDTO.getAvatarUrl(),
                Set.of("ROLE_USER"),
                userRegistrationDTO.getBirthdate(),
                userRegistrationDTO.getCreatedAt()


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
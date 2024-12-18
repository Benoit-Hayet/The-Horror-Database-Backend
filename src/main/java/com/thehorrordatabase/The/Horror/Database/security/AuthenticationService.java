package com.thehorrordatabase.The.Horror.Database.security;

import com.thehorrordatabase.The.Horror.Database.model.User;
import com.thehorrordatabase.The.Horror.Database.repository.UserRepository;
import com.thehorrordatabase.The.Horror.Database.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public String authenticate(String email, String password) {
        // Authentification de l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Récupérer les informations de l'utilisateur
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Générer le token avec l'ID utilisateur
        return jwtService.generateToken(userDetails, user.getId());
    }
}

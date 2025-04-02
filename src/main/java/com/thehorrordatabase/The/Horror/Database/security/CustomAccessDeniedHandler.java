package com.thehorrordatabase.The.Horror.Database.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403 Forbidden
        response.setContentType(APPLICATION_JSON_VALUE);

        Map<String, String> error = new HashMap<>();
        error.put("Accès refusé : ", "Oui");
        error.put("Erreur : ", "Vous n'avez pas les droits suffisants pour accéder a cette ressource");

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}

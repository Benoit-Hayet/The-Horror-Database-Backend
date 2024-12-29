package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.dto.UserReviewDTO;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.model.User;
import com.thehorrordatabase.The.Horror.Database.model.UserReview;
import com.thehorrordatabase.The.Horror.Database.repository.MovieRepository;
import com.thehorrordatabase.The.Horror.Database.repository.UserRepository;
import com.thehorrordatabase.The.Horror.Database.repository.UserReviewRepository;
import com.thehorrordatabase.The.Horror.Database.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reviews")
public class UserReviewController {

    private final UserReviewRepository userReviewRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public UserReviewController (UserReviewRepository userReviewRepository, JwtService jwtService, UserRepository userRepository, MovieRepository movieRepository) {
    this.userReviewRepository = userReviewRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;

    }

    @GetMapping
    public ResponseEntity<List<UserReviewDTO>> getAllUserReviews() {
        List < UserReview> userReviews = userReviewRepository.findAll();
        if (userReviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List <UserReviewDTO> userReviewDTOS = userReviews.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userReviewDTOS);
    }

    @GetMapping("/movie-review/{id}")
    public ResponseEntity<List<UserReviewDTO>> getUserReviewsByMovieId(@PathVariable Long id) {
        List<UserReview> userReviews = userReviewRepository.findByMovieId(id);
        if (userReviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserReviewDTO> userReviewDTOS = userReviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userReviewDTOS);
    }

    @PostMapping
    public ResponseEntity<UserReviewDTO> createUserReview(
            @RequestBody UserReview userReview,
            @RequestHeader("Authorization") String authorizationHeader) {
        // Extraire le token JWT de l'en-tête
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = jwtService.extractClaims(token);

        // Récupérer l'ID de l'utilisateur depuis les claims
        Long userId = claims.get("userId", Integer.class).longValue();

        // Charger l'utilisateur en base de données
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

        // Récupérer l'objet `Movie` à partir de l'ID dans l'objet `UserReview`
        if (userReview.getMovie() == null || userReview.getMovie().getId() == null) {
            throw new RuntimeException("L'ID du film est requis pour créer un avis.");
        }

        // Remplace cette ligne par l'initialisation de `MovieRepository` (voir étape 2)
        Movie movie = movieRepository.findById(userReview.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Film non trouvé avec l'ID : " + userReview.getMovie().getId()));

        // Associer le film et l'utilisateur à l'avis
        userReview.setMovie(movie);
        userReview.setUser(user);

        // Sauvegarder l'avis
        UserReview savedUserReview = userReviewRepository.save(userReview);

        // Convertir l'avis en DTO et retourner la réponse
        return ResponseEntity.status(201).body(convertToDTO(savedUserReview));
    }



    private UserReviewDTO convertToDTO(UserReview review) {
        UserReviewDTO reviewDTO = new UserReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setMovieId(review.getMovie().getId());
        reviewDTO.setReview(review.getReview());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCreatedAt(review.getCreatedAt());

        // Vérification de la nullité de l'utilisateur
        if (review.getUser() != null) {
            reviewDTO.setUserId(review.getUser().getId());
            reviewDTO.setUsername(review.getUser().getUsername());
            reviewDTO.setAvatarUrl(review.getUser().getAvatarUrl());
        } else {
            reviewDTO.setUserId(null);
            reviewDTO.setUsername(null);
            reviewDTO.setAvatarUrl(null);
        }

        return reviewDTO;
    }

}
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
import jakarta.persistence.PostUpdate;
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

    public UserReviewController(UserReviewRepository userReviewRepository, JwtService jwtService, UserRepository userRepository, MovieRepository movieRepository) {
        this.userReviewRepository = userReviewRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;

    }

    @GetMapping
    public ResponseEntity<List<UserReviewDTO>> getAllUserReviews() {
        List<UserReview> userReviews = userReviewRepository.findAll();
        if (userReviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserReviewDTO> userReviewDTOS = userReviews.stream().map(this::convertToDTO).collect(Collectors.toList());
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

    @GetMapping("/{id}")
    public ResponseEntity<List<UserReviewDTO>> getUserReviewsByUserId(@PathVariable Long id) {
        List<UserReview> userReviews = userReviewRepository.findReviewByUserId(id);
        if (userReviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserReviewDTO> userReviewDTOS = userReviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userReviewDTOS);
    }

    @PostMapping
    public ResponseEntity<UserReviewDTO> createUserReview(@RequestBody UserReview userReview, @RequestHeader("Authorization") String authorizationHeader) {
        // Log l'objet reçu
        System.out.println("Requête reçue avec UserReview : " + userReview);

        // Vérification du token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("Erreur : Authorization Header invalide.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.replace("Bearer ", "");

        // Extraction des claims
        Claims claims;
        try {
            claims = jwtService.extractClaims(token);
        } catch (Exception e) {
            System.out.println("Erreur : Token JWT invalide.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Récupération de l'ID utilisateur
        Long userId = claims.get("userId", Long.class);
        System.out.println("ID utilisateur extrait : " + userId);

        User user;
        try {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Vérification des données de l'avis
        if (userReview.getRating() < 1 || userReview.getRating() > 5) {
            System.out.println("Erreur : Note invalide (rating = " + userReview.getRating() + ")");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Vérification du film
        Movie movie;
        try {
            movie = movieRepository.findById(userReview.getMovie().getId())
                    .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Associer utilisateur et film
        userReview.setUser(user);
        userReview.setMovie(movie);

        // Sauvegarde
        UserReview savedUserReview;
        try {
            savedUserReview = userReviewRepository.save(userReview);
            System.out.println("Avis sauvegardé avec succès : " + savedUserReview);
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde de l'avis : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Conversion en DTO
        UserReviewDTO userReviewDTO = convertToDTO(savedUserReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(userReviewDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserReviewDTO> updateReview(@PathVariable Long id, @RequestBody UserReview userReview) {
        UserReview existingReview = userReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis non trouvé avec l'ID : " + id));

        existingReview.setReview(userReview.getReview());
        existingReview.setRating(userReview.getRating());

        UserReview updatedReview = userReviewRepository.save(existingReview);

        UserReviewDTO updatedReviewDTO = convertToDTO(updatedReview);
        return ResponseEntity.ok(updatedReviewDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserReview(@PathVariable Long id) {
        // Vérifier si l'avis existe
        UserReview userReview = userReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis non trouvé avec l'ID : " + id));

        userReviewRepository.delete(userReview);

        return ResponseEntity.noContent().build();
    }

    private UserReviewDTO convertToDTO(UserReview review) {
        UserReviewDTO reviewDTO = new UserReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setMovieId(review.getMovie().getId());
        reviewDTO.setReview(review.getReview());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCreatedAt(review.getCreatedAt());

        // Vérification que le film associé n'est pas nul
        if (review.getMovie() == null) {
            throw new IllegalStateException("The movie associated with this review is null.");
        }

        // Mapper les informations du film dans MovieDTO
        Movie movie = review.getMovie();
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setCountry(movie.getCountry());
        movieDTO.setReleaseYear(movie.getReleaseYear());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setSynopsis(movie.getSynopsis());
        movieDTO.setStatus(movie.getStatus());
        movieDTO.setPosterUrl(movie.getPosterUrl());
        movieDTO.setCreatedBy(movie.getCreatedBy());
        movieDTO.setCreatedAt(movie.getCreatedAt());

        // Ajouter les informations du film dans UserReviewDTO
        reviewDTO.setMovieDTOS(List.of(movieDTO)); // ou utilisez un seul MovieDTO si souhaité

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

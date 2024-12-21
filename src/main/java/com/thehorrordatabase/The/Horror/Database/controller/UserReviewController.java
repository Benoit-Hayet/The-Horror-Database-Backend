package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.dto.UserReviewDTO;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.model.UserReview;
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

    public UserReviewController (UserReviewRepository userReviewRepository, JwtService jwtService) {
    this.userReviewRepository = userReviewRepository;
        this.jwtService = jwtService;
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
public ResponseEntity<UserReviewDTO> createUserReview(@RequestBody UserReview userReview, @RequestHeader("Authorization") String authorizationHeader) {
    String token = authorizationHeader.replace("Bearer ", "");
    Claims claims = jwtService.extractClaims(token);
    Integer userId = claims.get("userId", Integer.class).intValue();
    //userId.setUserId(userId);
    UserReview savedUserReview =userReviewRepository.save(userReview);
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
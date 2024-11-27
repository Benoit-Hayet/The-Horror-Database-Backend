package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.UserReviewDTO;
import com.thehorrordatabase.The.Horror.Database.model.UserReview;
import com.thehorrordatabase.The.Horror.Database.repository.UserReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reviews")
public class UserReviewController {

    private final UserReviewRepository userReviewRepository;

    public UserReviewController (UserReviewRepository userReviewRepository) {
    this.userReviewRepository = userReviewRepository;

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
public ResponseEntity<UserReviewDTO> createUserReview(@RequestBody UserReview userReview) {
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
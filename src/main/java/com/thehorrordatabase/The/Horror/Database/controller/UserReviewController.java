package com.thehorrordatabase.The.Horror.Database.controller;


import com.thehorrordatabase.The.Horror.Database.dto.UserReviewDTO;
import com.thehorrordatabase.The.Horror.Database.model.UserReview;
import com.thehorrordatabase.The.Horror.Database.repository.UserReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping
    public ResponseEntity<List<UserReviewDTO>> getAllUserReviews() {
        List < UserReview> userReviews = userReviewRepository.findAll();
        if (userReviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List <UserReviewDTO> userReviewDTOS = userReviews.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userReviewDTOS);
    }



private UserReviewDTO convertToDTO(UserReview review) {
    UserReviewDTO reviewDTO = new UserReviewDTO();
    reviewDTO.setId(review.getId());
    reviewDTO.setReview(review.getReview());
    reviewDTO.setRating(review.getRating());
    reviewDTO.setCreatedAt(review.getCreatedAt());
    reviewDTO.setUserId(review.getUser().getId());
    reviewDTO.setUsername(review.getUser().getUsername());
    reviewDTO.setAvatarUrl(review.getUser().getAvatarUrl());
    return reviewDTO;

}
}
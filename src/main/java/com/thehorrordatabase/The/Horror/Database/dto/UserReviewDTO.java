package com.thehorrordatabase.The.Horror.Database.dto;

import java.time.LocalDate;
import java.util.List;

public class UserReviewDTO {


    private Long id;
    private Long movieId;
    private Long userId;
    private String review;
    private short rating;
    private LocalDate createdAt;
    private String username;
    private String avatarUrl;

    private List<MovieDTO>movieDTOS;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public short getRating() {
            return rating;
        }

        public void setRating(short rating) {
            this.rating = rating;
        }

        public LocalDate getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDate createdAt) {
            this.createdAt = createdAt;
        }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<MovieDTO> getMovieDTOS() {
        return movieDTOS;
    }

    public void setMovieDTOS(List<MovieDTO> movieDTOS) {
        this.movieDTOS = movieDTOS;
    }
}


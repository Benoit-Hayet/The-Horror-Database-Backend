package com.thehorrordatabase.The.Horror.Database.dto;

import java.time.LocalDate;
import java.util.List;

public class FavoriteDTO {

    private Long id;
    private Long movieId;
    private Long userId;
    private LocalDate createdAt;

    private List<MovieDTO> movieDTOS;

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<MovieDTO> getMovieDTOS() {
        return movieDTOS;
    }

    public void setMovieDTOS(List<MovieDTO> movieDTOS) {
        this.movieDTOS = movieDTOS;
    }
}

package com.thehorrordatabase.The.Horror.Database.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_Id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "country", nullable = false, length = 255)
    private String country;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "director", nullable = false, length = 100)
    private String director;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Mise en place du constructeur.

    public Movie() {
    }

    public Movie(String title, Integer releaseYear, String director, String synopsis, Status status, String posterUrl, Integer createdBy, LocalDateTime createdAt) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.synopsis = synopsis;
        this.status = status;
        this.posterUrl = posterUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // Mise en place des getter et setter

    public Long getMovieId() {
        return movie_Id;
    }

    public void setMovieId(Long movieId) {
        this.movie_Id = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Enum for Status
    public enum Status {
        REFUSED,
        PENDING,
        APPROVED
    }
}


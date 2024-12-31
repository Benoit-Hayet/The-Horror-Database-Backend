package com.thehorrordatabase.The.Horror.Database.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private EStatus status;

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private List<UserReview> userReviews;


    public Movie() {
    }

    public Movie(String title, String country, Integer releaseYear, String director, String synopsis, EStatus status, String posterUrl, Integer createdBy, LocalDateTime createdAt, List<Genre> genres) {
        this.title = title;
        this.country = country;
        this.releaseYear = releaseYear;
        this.director = director;
        this.synopsis = synopsis;
        this.status = status;
        this.posterUrl = posterUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.genres = genres;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<UserReview> getUserReview() {
        return userReviews;
    }

    public void setUserReview(List<UserReview> userReview) {
        this.userReviews = userReview;
    }
}
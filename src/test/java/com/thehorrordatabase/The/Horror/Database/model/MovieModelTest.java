package com.thehorrordatabase.The.Horror.Database.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieModelTest {

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
    }

    @Test
    void testMovieId() {
        Long id = 1L;
        movie.setId(id);
        assertEquals(id, movie.getId());
    }

    @Test
    void testMovieTitle() {
        String title = "Test Movie";
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());
    }

    @Test
    void testMovieCountry() {
        String country = "USA";
        movie.setCountry(country);
        assertEquals(country, movie.getCountry());
    }

    @Test
    void testMovieReleaseYear() {
        Integer releaseYear = 2025;
        movie.setReleaseYear(releaseYear);
        assertEquals(releaseYear, movie.getReleaseYear());
    }

    @Test
    void testMovieDirector() {
        String director = "John Doe";
        movie.setDirector(director);
        assertEquals(director, movie.getDirector());
    }

    @Test
    void testMovieSynopsis() {
        String synopsis = "A thrilling horror movie.";
        movie.setSynopsis(synopsis);
        assertEquals(synopsis, movie.getSynopsis());
    }

    @Test
    void testMovieStatus() {
        EStatus status = EStatus.PENDING;
        movie.setStatus(status);
        assertEquals(status, movie.getStatus());
    }

    @Test
    void testMoviePosterUrl() {
        String posterUrl = "http://example.com/poster.jpg";
        movie.setPosterUrl(posterUrl);
        assertEquals(posterUrl, movie.getPosterUrl());
    }

    @Test
    void testMovieCreatedBy() {
        Integer createdBy = 123;
        movie.setCreatedBy(createdBy);
        assertEquals(createdBy, movie.getCreatedBy());
    }

    @Test
    void testMovieCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        movie.setCreatedAt(createdAt);
        assertEquals(createdAt, movie.getCreatedAt());
    }

    @Test
    void testMovieGenres() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Horror");
        movie.setGenres(Collections.singletonList(genre));
        assertEquals(1, movie.getGenres().size());
        assertEquals("Horror", movie.getGenres().get(0).getName());
    }

    @Test
    void testMovieUserReviews() {
        UserReview userReview = new UserReview();
        userReview.setId(1L);
        movie.setUserReview(Collections.singletonList(userReview));
        assertEquals(1, movie.getUserReview().size());
        assertEquals(1L, movie.getUserReview().get(0).getId());
    }
}



package com.thehorrordatabase.The.Horror.Database.mapper;

import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.dto.UserReviewDTO;
import com.thehorrordatabase.The.Horror.Database.model.Genre;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.model.UserReview;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieDTO convertToDTO(Movie movie){
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
        if (movie.getGenres() != null) {
            movieDTO.setGenreName(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        }
        if (movie.getUserReview() != null) {
            movieDTO.setUserReview(movie.getUserReview().stream().map(this::convertToUserReviewDTO).collect(Collectors.toList()));
            //  userReviewDTo.setRating(movie.getUserReview().stream().map(UserReview::getRating).collect(Collectors.toList()));
        }


        return movieDTO;
    }

    public UserReviewDTO convertToUserReviewDTO(UserReview review) {
        UserReviewDTO reviewDTO = new UserReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReview(review.getReview());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setUserId(review.getUser().getId());
        reviewDTO.setUsername(review.getUser().getUsername()); // Récupération du username
        reviewDTO.setAvatarUrl(review.getUser().getAvatarUrl()); // Récupération de l'avatarUrl
        return reviewDTO;
    }

}


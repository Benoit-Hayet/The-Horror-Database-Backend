package com.thehorrordatabase.The.Horror.Database.service;
import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.mapper.MovieMapper;
import com.thehorrordatabase.The.Horror.Database.model.Genre;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.repository.GenreRepository;
import com.thehorrordatabase.The.Horror.Database.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    private final GenreRepository genreRepository;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.genreRepository = genreRepository;
    }


    public List<MovieDTO>getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movieMapper::convertToDTO).collect(Collectors.toList());
    }
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return null;
        }
        return movieMapper.convertToDTO((movie));
    }



    public MovieDTO createMovie(Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());

        if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
            List<Genre> validGenres = new ArrayList<>();
            for (Genre genre : movie.getGenres()) {
                if (genre.getId() != null) {
                    // Vérification des genres existants
                    Genre existingGenre = genreRepository.findById(genre.getId()).orElse(null);
                    if (existingGenre != null) {
                        validGenres.add(existingGenre);
                    } else {
                        return null;
                    }
                } else {
                    // Création d'un nouveau genre
                    Genre savedGenre = genreRepository.save(genre);
                    validGenres.add(savedGenre);
                }
            }
            movie.setGenres(validGenres);
        }

        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.convertToDTO(savedMovie);
    }


    public MovieDTO updateMovie(Long id,Movie movieDetails) {

        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return null;
        }

        movie.setTitle(movieDetails.getTitle());
        movie.setReleaseYear(movieDetails.getReleaseYear());
        movie.setDirector(movieDetails.getDirector());
        movie.setSynopsis(movieDetails.getSynopsis());
        movie.setStatus(movieDetails.getStatus());
        movie.setPosterUrl(movie.getPosterUrl());
        movie.setCountry(movieDetails.getCountry());

        if (movieDetails.getGenres() != null) {
            List<Genre> validGenres = new ArrayList<>();
            for (Genre genre : movieDetails.getGenres()) {
                if (genre.getId() != null) {
                    // Vérification d'un genre existant
                    Genre existingGenre = genreRepository.findById(genre.getId()).orElse(null);
                    if (existingGenre != null) {
                        validGenres.add(existingGenre);
                    } else {
                        return null;
                        // Genre non trouvé, retour d'une erreur
                    }
                } else {
                    // Création d'un nouveau genre
                    Genre savedGenre = genreRepository.save(genre);
                    validGenres.add(savedGenre);
                }
            }
            // Mettre à jour la liste des images associés
            movie.setGenres(validGenres);
        } else {
            // Si aucun genre n'est fournie, on nettoie la liste des genres associés
            movie.getGenres().clear();
        }

        Movie updatedMovie = movieRepository.save(movie);
        return movieMapper.convertToDTO(updatedMovie);
    }

    public boolean deleteMovie (Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return false;
        }
        genreRepository.deleteAll(movie.getGenres());
        movieRepository.delete(movie);
        return true;
    }

}

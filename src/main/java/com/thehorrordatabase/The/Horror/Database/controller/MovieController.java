package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.model.Genre;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.repository.GenreRepository;
import com.thehorrordatabase.The.Horror.Database.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<MovieDTO>movieDTOs = movies.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO>getMovieById(@PathVariable Long id) {
Optional<Movie> optionalMovie = movieRepository.findById(id);
if (!optionalMovie.isPresent()) {
    return ResponseEntity.notFound().build();
}
Movie movie = optionalMovie.get();
return ResponseEntity.ok(convertToDTO(movie));
    }


    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody Movie movie) {
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
                        return ResponseEntity.badRequest().body(null);
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
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedMovie));
    }
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {

        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return ResponseEntity.notFound().build();
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
                        return ResponseEntity.badRequest().build();
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
        return ResponseEntity.ok(convertToDTO(updatedMovie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
        return ResponseEntity.notFound().build();
    }
    movieRepository.delete(movie);
    return ResponseEntity.noContent().build();
}

    @GetMapping("/search-country")
    public ResponseEntity<List<Movie>> getMoviesByCountry(@RequestParam String searchTerms) {
        List<Movie> movie = movieRepository.findByCountry(searchTerms);
        if (movie.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movie);
    }
    @GetMapping("/search-between-release-date")
    public ResponseEntity<List<Movie>> getMoviesBetweenReleaseYear(@RequestParam Integer startYear, @RequestParam Integer endYear) {
        if (startYear > endYear) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Movie> movies = movieRepository.findByReleaseYearBetween(startYear, endYear);

        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    private MovieDTO convertToDTO(Movie movie){
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
        return movieDTO;
    }

    }


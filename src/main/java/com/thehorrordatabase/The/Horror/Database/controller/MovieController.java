package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> Movies = movieRepository.findAll();
        if (Movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie>getMovieById(@PathVariable Long id) {
Optional<Movie> optionalMovie = movieRepository.findById(id);
if (!optionalMovie.isPresent()) {
    return ResponseEntity.notFound().build();
}
return ResponseEntity.ok(optionalMovie.get());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
       Movie savedMovie = movieRepository.save(movie);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {

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

        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
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

}

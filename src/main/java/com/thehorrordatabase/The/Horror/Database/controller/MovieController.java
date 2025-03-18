package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.repository.GenreRepository;
import com.thehorrordatabase.The.Horror.Database.repository.MovieRepository;
import com.thehorrordatabase.The.Horror.Database.service.JwtService;
import com.thehorrordatabase.The.Horror.Database.service.MovieService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieService movieService;
    private final JwtService jwtService;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository, MovieService movieService, JwtService jwtService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.movieService = movieService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        MovieDTO movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<MovieDTO>> getMoviesByUser(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        Claims claims = jwtService.extractClaims(token);
        Long tokenUserId = claims.get("userId", Long.class);

        if (!userId.equals(tokenUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<MovieDTO> movieDTOs = movieService.getMoviesByUserId(userId);
        return ResponseEntity.ok(movieDTOs);
    }


    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = jwtService.extractClaims(token);
        Integer userId = claims.get("userId", Integer.class).intValue();
        movie.setCreatedBy(userId);
        MovieDTO savedMovie = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {

        MovieDTO updatedMovie = movieService.updateMovie(id, movieDetails);
        if (updatedMovie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = jwtService.extractClaims(token);

        // Vérifiez les rôles ou autorisations ici si nécessaire
        List<String> roles = claims.get("roles", List.class);
        System.out.println("Roles de l'utilisateur: " + roles);

        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


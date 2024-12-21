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
        this.movieService=movieService;
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

   @PostMapping
   public ResponseEntity<MovieDTO> createMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String authorizationHeader) {
       String token = authorizationHeader.replace("Bearer ", "");
       Claims claims = jwtService.extractClaims(token);
       Integer userId = claims.get("userId", Integer.class).intValue(); // Utilisation correcte du type Long
       movie.setCreatedBy(userId); // Utilisation de userId dans la méthode
       MovieDTO savedMovie = movieService.createMovie(movie);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
   }



    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {

        MovieDTO updatedMovie = movieService.updateMovie(id,movieDetails);
        if (updatedMovie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
}


package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.FavoriteDTO;
import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.model.Favorite;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.model.User;
import com.thehorrordatabase.The.Horror.Database.repository.FavoriteRepository;
import com.thehorrordatabase.The.Horror.Database.repository.MovieRepository;
import com.thehorrordatabase.The.Horror.Database.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thehorrordatabase.The.Horror.Database.service.JwtService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public FavoriteController(FavoriteRepository favoriteRepository,JwtService jwtService, UserRepository userRepository, MovieRepository movieRepository) {
        this.favoriteRepository = favoriteRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    // Get all favorites
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        List <Favorite> favorites = favoriteRepository.findAll();
        List<FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
    }


    // Get favorites by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUserId(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteRepository.findFavoriteByUserId(userId); // Assuming this method exists
        if (favorites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteDTO favoriteDTO, @RequestHeader("Authorization") String token) {
        // Valider le token JWT et obtenir les informations utilisateur
        Claims claims = jwtService.extractClaims(token.replace("Bearer ", ""));
        String username = claims.getSubject();
        Long userId = claims.get("userId", Integer.class).longValue();

        // Vérifier que l'utilisateur existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

        // Vérifier que le film existe
        Movie movie = movieRepository.findById(favoriteDTO.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        // Créer une nouvelle entité Favorite
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);
        favorite.setCreatedAt(LocalDate.now());

        // Sauvegarder dans le repository
        Favorite savedFavorite = favoriteRepository.save(favorite);


        // Retourner une réponse 201 CREATED avec le DTO
        return ResponseEntity.status(201).body(convertToDTO(savedFavorite));
    }


    // Delete a favorite by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavorite(@PathVariable Long id) {
        if (favoriteRepository.existsById(id)) {
            favoriteRepository.deleteById(id);
            return ResponseEntity.ok("Favorite removed successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to convert Favorite entity to DTO
    private FavoriteDTO convertToDTO(Favorite favorite) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setId(favorite.getId());
        favoriteDTO.setUserId(favorite.getUser().getId());
        favoriteDTO.setMovieId(favorite.getMovie().getId());
        favoriteDTO.setCreatedAt(favorite.getCreatedAt());


        // Vérification que le film associé n'est pas nul
        if (favorite.getMovie() == null) {
            throw new IllegalStateException("The movie associated with this review is null.");
        }

        // Mapper les informations du film dans MovieDTO
        Movie movie = favorite.getMovie();
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

        return favoriteDTO;
    }
}




package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.FavoriteDTO;
import com.thehorrordatabase.The.Horror.Database.model.Favorite;
import com.thehorrordatabase.The.Horror.Database.repository.FavoriteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    // Add a favorite
    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        // Convert DTO to entity
        Favorite favorite = new Favorite();
        favorite.setUserId(favoriteDTO.getUserId());
        favorite.setMovieId(favoriteDTO.getMovieId());
        favorite.setCreatedAt(LocalDate.now());

        // Save to database
        favorite = favoriteRepository.save(favorite);

        // Convert back to DTO and return
        return ResponseEntity.ok(convertToDTO(favorite));
    }

    // Get all favorites
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        List<FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
    }

    // Get favorites by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUserId(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId); // Assuming this method exists
        if (favorites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
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
        favoriteDTO.setUserId(favorite.getUserId());
        favoriteDTO.setMovieId(favorite.getMovieId());
        favoriteDTO.setCreatedAt(favorite.getCreatedAt());
        return favoriteDTO;
    }
}

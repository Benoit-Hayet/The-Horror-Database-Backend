package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.dto.GenreDTO;
import com.thehorrordatabase.The.Horror.Database.model.Genre;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/genres")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        if (genres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<GenreDTO> genreDTOs = genres.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(genreDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Long id) {
        Genre genreId = genreRepository.findById(id).orElse(null);
        if (genreId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(genreId));
    }


    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedGenre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updatedGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }
        genre.setName(genreDetails.getName());

        Genre updatedGenre = genreRepository.save(genre);
        return ResponseEntity.ok(convertToDTO(updatedGenre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }
        genreRepository.delete(genre);
        return ResponseEntity.noContent().build();
    }

    private GenreDTO convertToDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        if (genre.getMovies() != null) {
            genreDTO.setMovieIds(genre.getMovies().stream().map(Movie::getId).collect(Collectors.toList()));
        }
        return genreDTO;
    }
}
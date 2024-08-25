package com.thehorrordatabase.The.Horror.Database.controller;

import com.thehorrordatabase.The.Horror.Database.model.Genre;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @GetMapping
    public ResponseEntity <List<Genre>> getAllGenres() {
        List<Genre> Genres = genreRepository.findAll();
        if (Genres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre>getGenreById(@PathVariable Long id){
        Optional<Genre> GenreId = genreRepository.findById(id);
        if (GenreId.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(GenreId.get());
    }


@PostMapping
public ResponseEntity<Genre> createGenre(@RequestBody Genre genre){
    Genre savedGenre = genreRepository.save(genre);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
}

@PutMapping("/{id}")
public ResponseEntity<Genre> updatedGenre(@PathVariable Long id,@RequestBody Genre genreDetails){
 Genre genre = genreRepository.findById(id).orElse(null);
 if (genre == null){
     return ResponseEntity.notFound().build();
 }
 genre.setGenre(genreDetails.getGenre());

 Genre updatedGenre = genreRepository.save(genre);
 return ResponseEntity.ok(updatedGenre);
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


}
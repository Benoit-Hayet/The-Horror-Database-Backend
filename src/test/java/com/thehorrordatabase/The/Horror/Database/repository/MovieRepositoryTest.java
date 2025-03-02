package com.thehorrordatabase.The.Horror.Database.repository;

import com.thehorrordatabase.The.Horror.Database.model.EStatus;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        // Créer et enregistrer des films avant chaque test
        Movie movie1 = new Movie("Halloween", "USA", 1978, "John Carpenter", "Un tueur masqué...",
                EStatus.APPROVED, "https://example.com/halloween.jpg", 1, LocalDateTime.now(), List.of());

        Movie movie2 = new Movie("The Shining", "USA", 1980, "Stanley Kubrick", "Un hôtel hanté...",
                EStatus.APPROVED, "https://example.com/shining.jpg", 2, LocalDateTime.now(), List.of());

        movieRepository.saveAll(Arrays.asList(movie1, movie2));
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll(); // Nettoyer après chaque test
    }

    @Test
    void testFindAll_ShouldReturnAllMovies() {
        // WHEN
        List<Movie> movies = movieRepository.findAll();

        // THEN
        assertThat(movies).isNotNull();
        assertThat(movies).hasSize(2);
        assertThat(movies).extracting(Movie::getTitle).containsExactlyInAnyOrder("Halloween", "The Shining");
    }
}

package com.thehorrordatabase.The.Horror.Database.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thehorrordatabase.The.Horror.Database.dto.MovieDTO;
import com.thehorrordatabase.The.Horror.Database.model.EStatus;
import com.thehorrordatabase.The.Horror.Database.service.JwtService;
import com.thehorrordatabase.The.Horror.Database.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private MovieController movieController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void testGetAllMovies() throws Exception {
        List<MovieDTO> movies = Arrays.asList(
                new MovieDTO(1L, "Film 1", "USA", 2023, "Réalisateur 1", "Synopsis 1", EStatus.APPROVED, "poster1.jpg", 1, LocalDateTime.now(), Collections.singletonList("Horreur"), Collections.emptyList(), Collections.emptyList()),
                new MovieDTO(2L, "Film 2", "France", 2022, "Réalisateur 2", "Synopsis 2", EStatus.PENDING, "poster2.jpg", 2, LocalDateTime.now(), Collections.singletonList("Thriller"), Collections.emptyList(), Collections.emptyList())
        );
        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Film 1"))
                .andExpect(jsonPath("$[1].title").value("Film 2"));
    }

    @Test
    void testGetMovieById() throws Exception {
        MovieDTO movie = new MovieDTO(1L, "Film 1", "Horreur", 2023, "Réalisateur 1", "Synopsis 1", EStatus.APPROVED, "poster1.jpg", 1, LocalDateTime.now(), Collections.singletonList("Horreur"), Collections.emptyList(), Collections.emptyList());
        when(movieService.getMovieById(1L)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Film 1"));
    }

    /*@Test
    void testCreateMovie() throws Exception {
        MovieDTO movie = new MovieDTO(null, "Nouveau Film", "Fantastique", 2024, "Réalisateur 3", "Synopsis 3", EStatus.PENDING, "poster3.jpg", 3, LocalDateTime.now(), Collections.singletonList("Fantastique"), Collections.emptyList(), Collections.emptyList());
        MovieDTO savedMovie = new MovieDTO(1L, "Nouveau Film", "Fantastique", 2024, "Réalisateur 3", "Synopsis 3", EStatus.PENDING, "poster3.jpg", 3, LocalDateTime.now(), Collections.singletonList("Fantastique"), Collections.emptyList(), Collections.emptyList());
        when(movieService.createMovie(any(MovieDTO.class))).thenReturn(savedMovie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateMovie() throws Exception {
        MovieDTO updatedMovie = new MovieDTO(1L, "Film Modifié", "Science-Fiction", 2025, "Réalisateur 4", "Synopsis 4", EStatus.APPROVED, "poster4.jpg", 4, LocalDateTime.now(), Collections.singletonList("Science-Fiction"), Collections.emptyList(), Collections.emptyList());
        when(movieService.updateMovie(any(Long.class), any(MovieDTO.class))).thenReturn(updatedMovie);

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Film Modifié"));
    }*/

    @Test
    void testDeleteMovie() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());
    }
}

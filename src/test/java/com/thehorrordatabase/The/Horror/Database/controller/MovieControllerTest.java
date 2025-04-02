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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MovieControllerTest.class);
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
        logger.info("Setup completed.");
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
                .andExpect(jsonPath("$[1].title").value("Film 2"))
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void testGetMovieById() throws Exception {
        MovieDTO movie = new MovieDTO(1L, "Film 1", "Horreur", 2023, "Réalisateur 1", "Synopsis 1", EStatus.APPROVED, "poster1.jpg", 1, LocalDateTime.now(), Collections.singletonList("Horreur"), Collections.emptyList(), Collections.emptyList());
        when(movieService.getMovieById(1L)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Film 1"))
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void testDeleteMovie() throws Exception {
        when(movieService.deleteMovie(1L)).thenReturn(true);

        mockMvc.perform(delete("/movies/1")
                        .header("Authorization", "Bearer YOUR_TOKEN"))
                .andExpect(status().isNoContent())
                .andDo(result -> logger.info("Response: {}", result.getResponse().getContentAsString()));
    }
}

package com.thehorrordatabase.The.Horror.Database.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenreModelTest {

    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
    }

    @Test
    public void testGenreId() {
        Long id = 1L;
        genre.setId(id);
        assertEquals(id, genre.getId());
    }

    @Test
    public void testGenreName() {
        String name = "Gore";
        genre.setName(name);
        assertEquals(name, genre.getName());
    }
}

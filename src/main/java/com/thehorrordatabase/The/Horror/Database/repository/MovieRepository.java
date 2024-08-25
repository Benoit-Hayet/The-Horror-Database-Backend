package com.thehorrordatabase.The.Horror.Database.repository;

import com.thehorrordatabase.The.Horror.Database.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository <Movie,Long> {
    List<Movie> findByCountry(String country);
    List<Movie> findByReleaseYearBetween (Integer releaseYear1, Integer releaseYear2);
}

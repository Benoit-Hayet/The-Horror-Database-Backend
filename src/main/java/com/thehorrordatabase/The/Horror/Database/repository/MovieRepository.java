package com.thehorrordatabase.The.Horror.Database.repository;

import com.thehorrordatabase.The.Horror.Database.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository <Movie,Long> {

}

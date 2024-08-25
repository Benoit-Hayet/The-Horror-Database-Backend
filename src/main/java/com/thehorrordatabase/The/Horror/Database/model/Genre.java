package com.thehorrordatabase.The.Horror.Database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long genre_Id;

        private String genre;

    public Long getGenre_Id() {
        return genre_Id;
    }

    public void setGenre_Id(Long genre_Id) {
        this.genre_Id = genre_Id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}



package com.thehorrordatabase.The.Horror.Database.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;


    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    public Long getId() {
        return id;
    }

    public void setGenre_Id(Long genre_Id) {
        this.id = genre_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String genre) {
        this.name = name;
    }
}



package com.thehorrordatabase.The.Horror.Database.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fileName", nullable = false, length = 255)
    private String fileName;

    @Column(name = "filePath", nullable = false, length = 500)
    private String filePath;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime createdAt;

    // Relation avec l'utilisateur qui a uploadé le fichier
    @ManyToOne
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id", nullable = false)
    private User uploadedBy;

    // Relation avec un film si le fichier est une affiche de film
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = true)
    private Movie movie;

    // Catégorie d'upload : USER_AVATAR, MOVIE_POSTER, etc.
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

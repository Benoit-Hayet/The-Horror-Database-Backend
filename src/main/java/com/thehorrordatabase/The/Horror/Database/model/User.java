package com.thehorrordatabase.The.Horror.Database.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fist_name",nullable = false,length = 50 )
    private String first_name;

    @Column(name = "last_name", nullable = false,length = 50)
    private String last_name;

    @Column(name = "email",nullable = false,length = 255)
    private String email;

    @Column(name = "password",nullable = false,length = 50)
    private String password;

    @Column(name = "username",nullable = false,length = 50)
    private String username;

    @Column(name = "avatar_url",length = 500)
    private String avatar_url;

    @Column(name = "role",nullable = false,length = 50)
    private String role;

    @Column(name = "birthdate",length = 255)
    private LocalDate birthdate;

    @Column(name = "created_at",length = 255)
    private LocalDate created_at;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}

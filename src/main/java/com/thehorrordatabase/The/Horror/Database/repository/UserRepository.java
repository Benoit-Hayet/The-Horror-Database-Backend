package com.thehorrordatabase.The.Horror.Database.repository;

import com.thehorrordatabase.The.Horror.Database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User>findByUsername(String email);
}




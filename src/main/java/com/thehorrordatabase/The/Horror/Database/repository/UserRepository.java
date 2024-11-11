package com.thehorrordatabase.The.Horror.Database.repository;

import com.thehorrordatabase.The.Horror.Database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {
}

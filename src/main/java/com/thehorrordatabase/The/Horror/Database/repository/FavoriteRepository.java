package com.thehorrordatabase.The.Horror.Database.repository;

import com.thehorrordatabase.The.Horror.Database.model.Favorite;
import com.thehorrordatabase.The.Horror.Database.model.Movie;
import com.thehorrordatabase.The.Horror.Database.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository <Movie,Long> {
    List<Favorite> findFavoriteByUserId (Long userId);
}

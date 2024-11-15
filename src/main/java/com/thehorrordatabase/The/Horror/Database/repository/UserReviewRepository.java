package com.thehorrordatabase.The.Horror.Database.repository;


import com.thehorrordatabase.The.Horror.Database.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReviewRepository extends JpaRepository<UserReview, Long > {
    List<UserReview> findByMovieId(Long movieId);
}

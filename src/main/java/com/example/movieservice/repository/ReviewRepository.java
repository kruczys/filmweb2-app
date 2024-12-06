package com.example.movieservice.repository;

import com.example.movieservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Page<Review> findByMovieId(Long movieId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId")
    Double calculateAverageRatingForMovie(Long movieId);
}
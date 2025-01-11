package com.example.movieservice.repository;

import com.example.movieservice.model.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDateTime;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Page<Review> findByMovieId(Long movieId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId")
    Double calculateAverageRatingForMovie(Long movieId);

    @Query("SELECT r FROM Review r ORDER BY r.createdAt DESC")
    List<Review> findLatestReviews(Pageable pageable);

    List<Review> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT AVG(r.rating) FROM Review r")
    Double getAverageRating();

    @Query("SELECT COUNT(r) FROM Review r WHERE r.moderated = :moderated")
    long countByModerated(boolean moderated);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.createdAt < :date")
    long countByCreatedAtBefore(LocalDateTime date);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.createdAt > :date")
    long countByCreatedAtAfter(LocalDateTime date);
}
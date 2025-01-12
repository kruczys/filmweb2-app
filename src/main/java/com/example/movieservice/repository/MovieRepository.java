package com.example.movieservice.repository;

import com.example.movieservice.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.cast c WHERE LOWER(c.firstName) LIKE LOWER(concat('%', :name, '%')) OR LOWER(c.lastName) LIKE LOWER(concat('%', :name, '%'))")
    List<Movie> findByCastMemberName(@Param("name") String name);

    @Query("SELECT m FROM Movie m LEFT JOIN m.reviews r GROUP BY m ORDER BY COUNT(r) DESC")
    List<Movie> findMostPopularMovies();

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.name = :genreName")
    List<Movie> findByGenre(@Param("genreName") String genreName);

    @Query("SELECT COALESCE(SUM(m.viewCount), 0) FROM Movie m")
    Long getTotalViews();

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.createdAt > :date")
    long countByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.createdAt < :date")
    long countByCreatedAtBefore(LocalDateTime date);
}
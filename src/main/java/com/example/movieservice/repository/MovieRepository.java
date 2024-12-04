package com.example.movieservice.repository;

import com.example.movieservice.model.Actor;
import com.example.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT m FROM Movie m LEFT JOIN m.reviews r GROUP BY m ORDER BY COUNT(r) DESC")
    List<Movie> findMostPopularMovies();

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.name = :genreName")
    List<Movie> findByGenreName(@Param("genreName") String genreName);

    List<Movie> findByDirectorFirstNameContainingIgnoreCaseOrDirectorLastNameContainingIgnoreCase(
            String directorFirstName, String directorLastName);
}

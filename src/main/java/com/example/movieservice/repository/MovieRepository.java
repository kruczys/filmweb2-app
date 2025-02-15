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

    @Query(value = "SELECT DISTINCT m.* FROM movies m " +
           "LEFT JOIN movie_genre mg ON m.id = mg.movie_id " +
           "LEFT JOIN genres g ON mg.genre_id = g.id " +
           "LEFT JOIN movie_cast mc ON m.id = mc.movie_id " +
           "LEFT JOIN cast_members c ON mc.cast_member_id = c.id " +
           "WHERE (:title IS NULL OR m.title ILIKE CONCAT('%', :title, '%')) " +
           "AND (:genre IS NULL OR g.name ILIKE CONCAT('%', :genre, '%')) " +
           "AND (:castMember IS NULL OR CONCAT(c.first_name, ' ', c.last_name) ILIKE CONCAT('%', :castMember, '%'))",
           nativeQuery = true)
    Page<Movie> findBySearchCriteria(
        @Param("title") String title,
        @Param("genre") String genre,
        @Param("castMember") String castMember,
        Pageable pageable
    );

    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
    Page<Movie> findAllByOrderByReleaseDateDesc(Pageable pageable);

    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate ASC")
    Page<Movie> findAllByOrderByReleaseDateAsc(Pageable pageable);

    @Query(value = "SELECT m.* FROM movies m LEFT JOIN " +
           "(SELECT movie_id, AVG(rating) as avg_rating FROM reviews WHERE moderated = true GROUP BY movie_id) r " +
           "ON m.id = r.movie_id " +
           "ORDER BY CASE WHEN r.avg_rating IS NULL OR r.avg_rating = 0 THEN 2 ELSE 1 END, " +
           "r.avg_rating DESC", 
           nativeQuery = true)
    Page<Movie> findAllByOrderByAverageRatingDesc(Pageable pageable);

    @Query(value = "SELECT m.* FROM movies m LEFT JOIN " +
           "(SELECT movie_id, AVG(rating) as avg_rating FROM reviews WHERE moderated = true GROUP BY movie_id) r " +
           "ON m.id = r.movie_id " +
           "ORDER BY CASE WHEN r.avg_rating IS NULL OR r.avg_rating = 0 THEN 2 ELSE 1 END, " +
           "r.avg_rating ASC", 
           nativeQuery = true)
    Page<Movie> findAllByOrderByAverageRatingAsc(Pageable pageable);

    Page<Movie> findByAverageRatingGreaterThanEqual(Double minRating, Pageable pageable);

    @Query("SELECT m FROM Movie m ORDER BY CASE WHEN m.averageRating IS NULL THEN 0 ELSE m.averageRating END DESC")
    Page<Movie> findAllByOrderByAverageRatingDescNullsLast(Pageable pageable);

    @Query("SELECT m FROM Movie m ORDER BY CASE WHEN m.averageRating IS NULL THEN 5 ELSE m.averageRating END ASC")
    Page<Movie> findAllByOrderByAverageRatingAscNullsLast(Pageable pageable);
}
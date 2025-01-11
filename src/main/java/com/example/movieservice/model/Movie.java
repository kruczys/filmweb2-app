package com.example.movieservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @ManyToMany
    @JoinTable(
            name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cast_member_id")
    )
    private Set<CastMember> cast;

    @ManyToMany
    private Set<Genre> genres;

    private String imageUrl;

    private String trailerUrl;

    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews;

    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<User> favoritedBy;

    @ManyToMany(mappedBy = "watchList")
    private Set<User> inWatchlistOf;

    @ManyToMany(mappedBy = "ignoredMovies")
    private Set<User> ignoredBy;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public double getAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
package com.example.movieservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Table(name = "movies")
@JsonIgnoreProperties({"reviews", "favoritedBy"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @ManyToMany
    @JoinTable(
            name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cast_member_id")
    )
    private Set<CastMember> cast = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    private String imageUrl;
    private String trailerUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @JsonManagedReference
    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<User> favoritedBy = new HashSet<>();

    @ManyToMany(mappedBy = "watchList")
    private Set<User> inWatchlistOf = new HashSet<>();

    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        viewCount = 0L;
    }

    public void updateAverageRating() {
        if (this.reviews == null || this.reviews.isEmpty()) {
            this.averageRating = 0.0;
            return;
        }

        double sum = this.reviews.stream()
            .filter(review -> review.isModerated())
            .mapToDouble(Review::getRating)
            .sum();

        long count = this.reviews.stream()
            .filter(review -> review.isModerated())
            .count();

        this.averageRating = count > 0 ? sum / count : 0.0;
    }

    public Double getAverageRating() {
        if (this.averageRating == null) {
            updateAverageRating();
        }
        return this.averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getId(), movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
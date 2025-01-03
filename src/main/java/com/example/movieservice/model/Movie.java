package com.example.movieservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private LocalDate releaseDate;

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
}
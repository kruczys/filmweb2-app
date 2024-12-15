package com.example.movieservice.model;


import com.example.movieservice.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Column(unique = true)
    @NotBlank
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private Set<Movie> favoriteMovies;

    @ManyToMany
    private Set<Movie> watchList;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    @ManyToMany
    private Set<Movie> ignoredMovies;
}

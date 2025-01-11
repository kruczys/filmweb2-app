package com.example.movieservice.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import com.example.movieservice.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private boolean enabled = true;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private Set<Movie> favoriteMovies = new HashSet<>();

    @ManyToMany
    private Set<Movie> watchList = new HashSet<>();

    @ManyToMany
    private Set<Movie> ignoredMovies = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
        lastLogin = LocalDateTime.now();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(Set<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public Set<Movie> getWatchList() {
        return watchList;
    }

    public void setWatchList(Set<Movie> watchList) {
        this.watchList = watchList;
    }

    public Set<Movie> getIgnoredMovies() {
        return ignoredMovies;
    }

    public void setIgnoredMovies(Set<Movie> ignoredMovies) {
        this.ignoredMovies = ignoredMovies;
    }
}

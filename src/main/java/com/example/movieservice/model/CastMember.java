package com.example.movieservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@Entity
@Table(name = "cast_members")
public class CastMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "text")
    private String firstName;

    @NotBlank
    @Column(columnDefinition = "text")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(columnDefinition = "text")
    private String biography;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "cast")
    private Set<Movie> movies = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (dateOfBirth == null) {
            dateOfBirth = LocalDateTime.now();
        }
    }
}
package com.example.movieservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();
}
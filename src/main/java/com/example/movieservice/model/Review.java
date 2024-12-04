package com.example.movieservice.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @ManyToOne
    private User user;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "review")
    private Set<Comment> comments;

    private LocalDate createdAt;
}

package com.example.movieservice.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.example.movieservice.model.Review;

import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String imageUrl;
    private String trailerUrl;
    private List<Long> genreIds = new ArrayList<>();
    private List<Long> castMemberIds = new ArrayList<>();
    private Double averageRating;
} 
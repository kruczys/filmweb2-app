package com.example.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStats {
    private Long movieCount;
    private Long userCount;
    private Long reviewCount;
    private Long genreCount;
    
    private Long totalViews;
    private Double averageRating;
    private Long activeUsers;
    private Long pendingReviews;
    
    private Double userGrowthRate;
    private Double reviewGrowthRate;
    
    private Long monthlyActiveUsers;
    private Long monthlyNewReviews;
    private Long monthlyNewMovies;
} 
package com.example.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStats {
    private long movieCount;
    private long userCount;
    private long reviewCount;
    private long genreCount;
    
    private long totalViews;
    private double averageRating;
    private long activeUsers;
    private long pendingReviews;
    
    private double userGrowthRate;
    private double reviewGrowthRate;
    
    private long monthlyActiveUsers;
    private long monthlyNewReviews;
    private long monthlyNewMovies;
} 
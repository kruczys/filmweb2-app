package com.example.movieservice.service;

import com.example.movieservice.dto.AdminDashboardStats;
import com.example.movieservice.dto.ActivityData;
import com.example.movieservice.model.*;
import com.example.movieservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AdminService {
    private static final java.time.format.DateTimeFormatter DATE_FORMATTER = 
        java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public AdminDashboardStats getDashboardStats() {
        AdminDashboardStats stats = new AdminDashboardStats();
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);
        
        // Podstawowe liczniki
        stats.setMovieCount(movieRepository.count());
        stats.setUserCount(userRepository.count());
        stats.setReviewCount(reviewRepository.count());
        stats.setGenreCount(genreRepository.count());
        
        // Statystyki ogólne
        stats.setTotalViews(movieRepository.getTotalViews() != null ? movieRepository.getTotalViews() : 0L);
        Double avgRating = reviewRepository.getAverageRating();
        stats.setAverageRating(avgRating != null ? avgRating : 0.0);
        stats.setActiveUsers(userRepository.countByLastLoginAfter(monthAgo));
        stats.setPendingReviews(reviewRepository.countByModerated(false));
        
        // Wskaźniki wzrostu
        long previousMonthUsers = userRepository.countByRegistrationDateBefore(monthAgo);
        long currentMonthUsers = userRepository.countByRegistrationDateAfter(monthAgo);
        stats.setUserGrowthRate(calculateGrowthRate(previousMonthUsers, currentMonthUsers));
        
        long previousMonthReviews = reviewRepository.countByCreatedAtBefore(monthAgo);
        long currentMonthReviews = reviewRepository.countByCreatedAtAfter(monthAgo);
        stats.setReviewGrowthRate(calculateGrowthRate(previousMonthReviews, currentMonthReviews));
        
        // Statystyki miesięczne
        stats.setMonthlyActiveUsers(userRepository.countByLastLoginAfter(monthAgo));
        stats.setMonthlyNewReviews(reviewRepository.countByCreatedAtAfter(monthAgo));
        stats.setMonthlyNewMovies(movieRepository.countByCreatedAtAfter(monthAgo));
        
        return stats;
    }

    private double calculateGrowthRate(long previous, long current) {
        if (previous == 0) return current > 0 ? 100.0 : 0.0;
        return ((double) (current - previous) / previous) * 100.0;
    }

    public ActivityData getActivityData() {
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        
        List<Review> recentReviews = reviewRepository.findByCreatedAtAfter(weekAgo);
        List<User> recentUsers = userRepository.findByRegistrationDateAfter(weekAgo);

        Map<String, Long> reviewsByDay = recentReviews.stream()
                .collect(Collectors.groupingBy(
                        review -> review.getCreatedAt().format(DATE_FORMATTER),
                        HashMap::new,
                        Collectors.counting()
                ));

        Map<String, Long> usersByDay = recentUsers.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getRegistrationDate().format(DATE_FORMATTER),
                        HashMap::new,
                        Collectors.counting()
                ));

        return new ActivityData(reviewsByDay, usersByDay);
    }

    @Transactional
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public Page<Movie> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<Review> getReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    public void deleteGenre(Long genreId) {
        genreRepository.deleteById(genreId);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
    }

    @Transactional
    public User updateUser(Long id, User userUpdate) {
        User user = getUserById(id);
        user.setEmail(userUpdate.getEmail());
        user.setEnabled(userUpdate.isEnabled());
        return userRepository.save(user);
    }
} 
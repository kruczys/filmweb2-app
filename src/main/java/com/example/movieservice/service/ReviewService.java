package com.example.movieservice.service;

import com.example.movieservice.model.Review;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.repository.ReviewRepository;
import com.example.movieservice.repository.MovieRepository;
import com.example.movieservice.repository.UserRepository;
import com.example.movieservice.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Page<Review> getMovieReviews(Long movieId, Pageable pageable) {
        return reviewRepository.findByMovieId(movieId, pageable);
    }

    public Page<Review> getUserReviews(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable);
    }

    public Review addReview(Long userId, Long movieId, Review review) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new RuntimeException("Nie znaleziono filmu"));
        
        review.setUser(user);
        review.setMovie(movie);
        review.setModerated(true);
        review.setCreatedAt(LocalDateTime.now());
        
        Review savedReview = reviewRepository.save(review);
        
        movie.updateAverageRating();
        movieRepository.save(movie);
        
        return savedReview;
    }

    public Review updateReview(Long reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Nie znaleziono recenzji"));
        
        if (reviewDetails.getRating() != 0) review.setRating(reviewDetails.getRating());
        if (reviewDetails.getContent() != null) review.setContent(reviewDetails.getContent());
        
        Review savedReview = reviewRepository.save(review);
        Movie movie = review.getMovie();
        movie.updateAverageRating();
        movieRepository.save(movie);
        
        return savedReview;
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Nie znaleziono recenzji"));
        
        Movie movie = review.getMovie();
        
        reviewRepository.delete(review);
        
        movie.getReviews().remove(review);
        movie.updateAverageRating();
        movieRepository.save(movie);
    }

    public Double getAverageMovieRating(Long movieId) {
        return reviewRepository.calculateAverageRatingForMovie(movieId);
    }

    public List<Review> getLatestReviews() {
        return reviewRepository.findLatestReviews(PageRequest.of(0, 10));
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nie znaleziono recenzji"));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
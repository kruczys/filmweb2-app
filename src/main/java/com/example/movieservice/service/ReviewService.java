package com.example.movieservice.service;

import com.example.movieservice.model.Review;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository,
                         MovieService movieService,
                         UserService userService) {
        this.reviewRepository = reviewRepository;
        this.movieService = movieService;
        this.userService = userService;
    }

    public Page<Review> getMovieReviews(Long movieId, Pageable pageable) {
        return reviewRepository.findByMovieId(movieId, pageable);
    }

    public Page<Review> getUserReviews(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable);
    }

    public Review addReview(Long userId, Long movieId, Review review) {
        User user = userService.getUserById(userId);
        Movie movie = movieService.getMovieById(movieId);

        review.setUser(user);
        review.setMovie(movie);

        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (reviewDetails.getRating() != 0) review.setRating(reviewDetails.getRating());
        if (reviewDetails.getContent() != null) review.setContent(reviewDetails.getContent());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Double getAverageMovieRating(Long movieId) {
        return reviewRepository.calculateAverageRatingForMovie(movieId);
    }

    public List<Review> getLatestReviews() {
        return reviewRepository.findLatestReviews(PageRequest.of(0, 10));
    }
}
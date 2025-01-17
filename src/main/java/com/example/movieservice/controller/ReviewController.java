package com.example.movieservice.controller;

import com.example.movieservice.model.Review;
import com.example.movieservice.model.User;
import com.example.movieservice.service.ReviewService;
import com.example.movieservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Page<Review>> getMovieReviews(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reviewService.getMovieReviews(movieId, PageRequest.of(page, size)));
    }

    @PostMapping("/movie/{movieId}/user/{userId}")
    public ResponseEntity<Review> addReview(
            @PathVariable Long movieId,
            @PathVariable Long userId,
            @RequestBody Review review
    ) {
        try {
            Review savedReview = reviewService.addReview(userId, movieId, review);
            return ResponseEntity.ok(savedReview);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/{movieId}")
    @ResponseBody
    public ResponseEntity<Review> addReview(@PathVariable Long movieId, 
                                          @RequestBody Review review,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByUsername(userDetails.getUsername());
            Review savedReview = reviewService.addReview(user.getId(), movieId, review);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
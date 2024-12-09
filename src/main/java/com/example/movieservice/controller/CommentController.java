package com.example.movieservice.controller;

import com.example.movieservice.model.Comment;
import com.example.movieservice.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Page<Comment>> getReviewComments(
            @PathVariable Long reviewId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getReviewComments(reviewId, PageRequest.of(page, size)));
    }

    @PostMapping("/review/{reviewId}/user/{userId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long reviewId,
            @PathVariable Long userId,
            @RequestBody Comment comment
    ) {
        return ResponseEntity.ok(commentService.addComment(userId, reviewId, comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(id, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
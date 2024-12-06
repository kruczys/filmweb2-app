package com.example.movieservice.service;

import com.example.movieservice.model.Comment;
import com.example.movieservice.model.Review;
import com.example.movieservice.model.User;
import com.example.movieservice.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ReviewService reviewService;

    public CommentService(CommentRepository commentRepository,
                          UserService userService,
                          ReviewService reviewService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    public Page<Comment> getReviewComments(Long reviewId, Pageable pageable) {
        return commentRepository.findByReviewId(reviewId, pageable);
    }

    @Transactional
    public Comment addComment(Long userId, Long reviewId, Comment comment) {
        User user = userService.getUserById(userId);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, Comment commentDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (commentDetails.getContent() != null) {
            comment.setContent(commentDetails.getContent());
        }

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Page<Comment> getUserComments(Long userId, Pageable pageable) {
        return commentRepository.findByUserId(userId, pageable);
    }
}
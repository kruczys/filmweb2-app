package com.example.movieservice.repository;

import com.example.movieservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long reviewId);

    Page<Comment> findByUserId(Long userId, Pageable pageable);

    Page<Comment> findByReviewId(Long reviewId, Pageable pageable);

    Page<Comment> findByOrderByCreatedAtDesc(Pageable pageable);

    void deleteByReviewId(Long reviewId);
}
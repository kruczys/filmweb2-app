package com.example.movieservice.repository;

import com.example.movieservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN u.reviews r GROUP BY u ORDER BY COUNT(r) DESC")
    List<User> findMostActiveUsers();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}

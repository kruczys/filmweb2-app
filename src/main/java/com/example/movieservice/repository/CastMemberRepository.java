package com.example.movieservice.repository;

import com.example.movieservice.model.CastMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CastMemberRepository extends JpaRepository<CastMember, Long> {
    List<CastMember> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);
}

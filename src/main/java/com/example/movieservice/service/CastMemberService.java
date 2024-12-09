package com.example.movieservice.service;

import com.example.movieservice.model.CastMember;
import com.example.movieservice.repository.CastMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Transactional
public class CastMemberService {
    private final CastMemberRepository castMemberRepository;

    public CastMemberService(CastMemberRepository castMemberRepository) {
        this.castMemberRepository = castMemberRepository;
    }

    public CastMember getCastMemberById(Long id) {
        return castMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cast member not found with id: " + id));
    }

    public List<CastMember> searchCastMembers(String name) {
        return castMemberRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    public CastMember addCastMember(CastMember castMember) {
        return castMemberRepository.save(castMember);
    }

    public CastMember updateCastMember(Long id, CastMember castMemberDetails) {
        CastMember castMember = getCastMemberById(id);

        if (castMemberDetails.getFirstName() != null) castMember.setFirstName(castMemberDetails.getFirstName());
        if (castMemberDetails.getLastName() != null) castMember.setLastName(castMemberDetails.getLastName());
        if (castMemberDetails.getDateOfBirth() != null) castMember.setDateOfBirth(castMemberDetails.getDateOfBirth());
        if (castMemberDetails.getBiography() != null) castMember.setBiography(castMemberDetails.getBiography());
        if (castMemberDetails.getImageUrl() != null) castMember.setImageUrl(castMemberDetails.getImageUrl());

        return castMemberRepository.save(castMember);
    }

    public void deleteCastMember(Long id) {
        castMemberRepository.deleteById(id);
    }

    public Page<CastMember> getAllCastMembers(Pageable pageable) {
        return castMemberRepository.findAll(pageable);
    }
}
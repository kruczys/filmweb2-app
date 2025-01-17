package com.example.movieservice.controller;

import com.example.movieservice.model.CastMember;
import com.example.movieservice.service.CastMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cast-members")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCastMemberController {
    private final CastMemberService castMemberService;

    public AdminCastMemberController(CastMemberService castMemberService) {
        this.castMemberService = castMemberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastMember> getCastMember(@PathVariable Long id) {
        return ResponseEntity.ok(castMemberService.getCastMemberById(id));
    }

    @PostMapping
    public ResponseEntity<CastMember> addCastMember(@RequestBody CastMember castMember) {
        return ResponseEntity.ok(castMemberService.addCastMember(castMember));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CastMember> updateCastMember(@PathVariable Long id, @RequestBody CastMember castMember) {
        try {
            castMember.setId(id);
            CastMember updated = castMemberService.updateCastMember(id, castMember);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCastMember(@PathVariable Long id) {
        castMemberService.deleteCastMember(id);
        return ResponseEntity.ok().build();
    }
} 
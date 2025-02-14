package com.example.movieservice.controller;

import com.example.movieservice.model.CastMember;
import com.example.movieservice.service.CastMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cast-members")
public class CastMemberRestController {

    @Autowired
    private CastMemberService castMemberService;

    @GetMapping
    public ResponseEntity<List<CastMember>> getAllCastMembers() {
        List<CastMember> castMembers = castMemberService.getAllCastMembers();
        return ResponseEntity.ok(castMembers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastMember> getCastMemberById(@PathVariable Long id) {
        CastMember castMember = castMemberService.getCastMemberById(id);
        return ResponseEntity.ok(castMember);
    }

    @PostMapping
    public ResponseEntity<CastMember> createCastMember(@RequestBody CastMember castMember) {
        CastMember created = castMemberService.addCastMember(castMember);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CastMember> updateCastMember(@PathVariable Long id, @RequestBody CastMember castMember) {
        castMember.setId(id);
        CastMember updated = castMemberService.updateCastMember(id, castMember);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCastMember(@PathVariable Long id) {
        castMemberService.deleteCastMember(id);
        return ResponseEntity.ok().build();
    }
}

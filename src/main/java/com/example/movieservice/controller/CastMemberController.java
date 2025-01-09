package com.example.movieservice.controller;

import com.example.movieservice.model.CastMember;
import com.example.movieservice.service.CastMemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/cast-members")
@CrossOrigin(origins = "*")
public class CastMemberController {
    private final CastMemberService castMemberService;

    public CastMemberController(CastMemberService castMemberService) {
        this.castMemberService = castMemberService;
    }

    @GetMapping
    public ResponseEntity<Page<CastMember>> getAllCastMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(castMemberService.getAllCastMembers(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastMember> getCastMember(@PathVariable Long id) {
        return ResponseEntity.ok(castMemberService.getCastMemberById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CastMember>> searchCastMembers(@RequestParam String name) {
        return ResponseEntity.ok(castMemberService.searchCastMembers(name));
    }

    @PostMapping
    public ResponseEntity<CastMember> addCastMember(@RequestBody CastMember castMember) {
        return ResponseEntity.ok(castMemberService.addCastMember(castMember));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CastMember> updateCastMember(
            @PathVariable Long id,
            @RequestBody CastMember castMember
    ) {
        return ResponseEntity.ok(castMemberService.updateCastMember(id, castMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCastMember(@PathVariable Long id) {
        castMemberService.deleteCastMember(id);
        return ResponseEntity.ok().build();
    }
}
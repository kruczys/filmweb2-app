package com.example.movieservice.controller;

import com.example.movieservice.model.Genre;
import com.example.movieservice.model.User;
import com.example.movieservice.service.AdminService;
import com.example.movieservice.service.CastMemberService;
import com.example.movieservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final GenreService genreService;
    private final CastMemberService castMemberService;
    
    @GetMapping
    public String dashboard(Model model, Pageable pageable) {
        model.addAttribute("stats", adminService.getDashboardStats());
        model.addAttribute("movies", adminService.getMovies(pageable));
        model.addAttribute("users", adminService.getUsers(pageable));
        model.addAttribute("reviews", adminService.getReviews(pageable));
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("castMembers", castMemberService.getAllCastMembers());
        return "admin/dashboard";
    }

    @PostMapping("/users/{id}/block")
    @ResponseBody
    public void blockUser(@PathVariable Long id) {
        adminService.blockUser(id);
    }

    @PostMapping("/users/{id}/unblock")
    @ResponseBody
    public void unblockUser(@PathVariable Long id) {
        adminService.unblockUser(id);
    }

    @DeleteMapping("/reviews/{id}")
    @ResponseBody
    public void deleteReview(@PathVariable Long id) {
        adminService.deleteReview(id);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseBody
    public void deleteComment(@PathVariable Long id) {
        adminService.deleteComment(id);
    }

    @GetMapping("/genres")
    @ResponseBody
    public List<Genre> getGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping("/genres")
    @ResponseBody
    public Genre addGenre(@RequestBody Genre genre) {
        return adminService.addGenre(genre);
    }

    @DeleteMapping("/genres/{id}")
    @ResponseBody
    public void deleteGenre(@PathVariable Long id) {
        adminService.deleteGenre(id);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = adminService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userUpdate) {
        User updatedUser = adminService.updateUser(id, userUpdate);
        return ResponseEntity.ok(updatedUser);
    }
} 
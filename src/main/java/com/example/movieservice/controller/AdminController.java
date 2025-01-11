package com.example.movieservice.controller;

import com.example.movieservice.dto.AdminDashboardStats;
import com.example.movieservice.dto.ActivityData;
import com.example.movieservice.model.Genre;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping
    public String dashboard(Model model) {
        AdminDashboardStats stats = adminService.getDashboardStats();
        ActivityData activityData = adminService.getActivityData();
        
        model.addAttribute("stats", stats);
        model.addAttribute("activityData", activityData);
        model.addAttribute("movies", adminService.getMovies(PageRequest.of(0, 10)));
        model.addAttribute("users", adminService.getUsers(PageRequest.of(0, 10)));
        
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

    @DeleteMapping("/movies/{id}")
    @ResponseBody
    public void deleteMovie(@PathVariable Long id) {
        adminService.deleteMovie(id);
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
        return adminService.getAllGenres();
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
} 
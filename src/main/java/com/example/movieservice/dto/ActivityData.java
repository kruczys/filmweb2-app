package com.example.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityData {
    private Map<String, Long> reviewsByDay;
    private Map<String, Long> usersByDay;
} 
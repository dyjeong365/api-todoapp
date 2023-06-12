package com.codestates.todoapp.recommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    // test
    @GetMapping("/")
    public String helloWorld(){
        return "To-do Application !";
    }
}

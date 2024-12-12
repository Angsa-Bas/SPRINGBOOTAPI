package com.domain.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/controller")
public class WelcomController {
    
    @GetMapping
    public String welcome(){
        return "Welcome To Springboot Rest Api";
    }
}

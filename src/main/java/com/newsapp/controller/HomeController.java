package com.newsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class HomeController {

    @GetMapping("/login")
    public String home(){
        return "login";
    }

    @GetMapping("/home/{userId}")
    public String home(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId); // Pass userId to the frontend
        return "home"; // Render home.html
    }

    
}

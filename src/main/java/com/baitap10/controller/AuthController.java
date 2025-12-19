package com.baitap10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("login")
    public String loginPage() {
        // Trả về file login.html trong thư mục src/main/resources/templates
        return "login";
    }

    @GetMapping("user/profile")
    public String profilePage() {
        // Trả về file profile.html trong thư mục src/main/resources/templates
        return "profile";
    }
}

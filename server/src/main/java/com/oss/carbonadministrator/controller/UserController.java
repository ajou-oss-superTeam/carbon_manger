package com.oss.carbonadministrator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/user-email/exists")
    public String checkEmailDuplicated() {
        return "ok";
    }

    @PostMapping("/user-nickname/exists")
    public String checkNicknameDuplicated() {
        return "ok";
    }


    @PostMapping("/signup")
    public String signup() {
        return "ok";
    }

    @PostMapping("/login")
    public String login() {
        return "ok";
    }


}

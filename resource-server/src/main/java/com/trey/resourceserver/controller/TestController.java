package com.trey.resourceserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("meet")
    public String meet() {
        return "I meet you";
    }

    @GetMapping("welcome")
    public String welcome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Welcome " + authentication;
    }

    @GetMapping("project")
    @PreAuthorize("hasRole('ROLE_PROJECT_ADMIN')")
    public String project() {
        return "This is my project";
    }
}
package com.StartupReview.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, RAJAT here the time at the server is now " + new Date() + "\n";
    }
}

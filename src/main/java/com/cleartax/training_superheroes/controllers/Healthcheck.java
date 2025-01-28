package com.cleartax.training_superheroes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Healthcheck {
    @GetMapping("/health-check")
    public String gethealth(){
        return "OK server";
    }
}

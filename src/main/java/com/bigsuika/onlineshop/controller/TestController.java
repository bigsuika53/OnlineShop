package com.bigsuika.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "The Start?")
public class TestController {
    @GetMapping("/hello")
    @Operation(summary = "Hello, Online Shop", description = "The first function!")
    public String hello() {
        return "Hello, Online Shop!";
    }
}

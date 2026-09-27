package com.yash.api_error_lab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @GetMapping("/test-error")
    public void testError() {
        throw new IllegalStateException("Something went wrong");
    }
    @GetMapping("/test-unexpected-error")
    public void testUnexpectedError() {

        throw new RuntimeException("SECRET INTERNAL DETAIL");
    }
}

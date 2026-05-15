package com.dikshant.codesphere_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dikshant.codesphere_backend.service.DockerExecutionService;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    @Autowired
    private DockerExecutionService dockerExecutionService;

    // Request body model
    public record CodeRequest(String code, String language, String input) {}

    @PostMapping("/run")
    public Map<String, Object> runCode(@RequestBody CodeRequest req) {

        return dockerExecutionService.runCode(
                req.code(),
                req.language(),
                req.input()
        );
    }
}
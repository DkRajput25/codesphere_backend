package com.dikshant.codesphere_backend.controller;

import com.dikshant.codesphere_backend.service.AIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/explain")
    public String explainCode(@RequestBody Map<String,String> req){

        String code = req.get("code");

        String prompt =
                "Explain this code in very simple beginner language. Maximum 4 short bullet points. Do not write long paragraphs:\n"
                        + code;

        return aiService.askAI(prompt);
    }

    @PostMapping("/fix")
    public String fixCode(@RequestBody Map<String,String> req){

        String code = req.get("code");

        String prompt =
                "Find the bug in this code. Explain the mistake in 2 lines and then give the corrected code:\n"
                        + code;

        return aiService.askAI(prompt);
    }

    @PostMapping("/generate")
    public String generateCode(@RequestBody Map<String,String> req){

        String prompt = req.get("prompt");

        String fullPrompt =
                "Generate clean and simple code for this request without markdown . Do not add explanation. Only give code:\n" + prompt;

        return aiService.askAI(fullPrompt);
    }
}
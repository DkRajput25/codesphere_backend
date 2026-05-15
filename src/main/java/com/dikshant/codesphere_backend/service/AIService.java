package com.dikshant.codesphere_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askAI(String prompt) {

        try {

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-goog-api-key", apiKey);

            Map<String,Object> text = new HashMap<>();
            text.put("text", prompt);

            Map<String,Object> part = new HashMap<>();
            part.put("parts", List.of(text));

            Map<String,Object> body = new HashMap<>();
            body.put("contents", List.of(part));

            HttpEntity<Map<String,Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            // JSON parse karna
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            String aiText =
                    root
                            .path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

            return aiText;

        } catch (Exception e) {

            e.printStackTrace();
            return "AI Error: " + e.getMessage();
        }
    }
}
package com.project.dramatic_advice.infrastructure.ai;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

public class GeminiReWriter {

    private final String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public GeminiReWriter(String apiKey) {
        this.apiKey = apiKey;
    }

    public String rewrite(String input) {

        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        String body = """
    {
      "contents": [
        {
          "parts": [
            {"text": "Rewrite this in a dramatic philosophical life advice style: %s"}
          ]
        }
      ]
    }
    """.formatted(input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        GeminiResponse gemini = mapper.readValue(response.getBody(), GeminiResponse.class);
        return gemini.candidates.get(0).content.parts.get(0).text;
    }
}

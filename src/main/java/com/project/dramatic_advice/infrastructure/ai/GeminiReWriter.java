package com.project.dramatic_advice.infrastructure.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GeminiReWriter {

    private final String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public GeminiReWriter(String apiKey) {
        this.apiKey = apiKey;
    }

    public String rewrite(String text, String mood) {

        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        String prompt = """
            You are a dramatic advice generator.
            
            User mood: %s
            User thought: "%s"
            
            Rules:
            - Reply in only 2-3 short lines.
            - Use simple, beginner-friendly English.
            - Do not use heavy poetic language.
            - Do not exceed 40 words.
            - Make tone match the mood.
            - Keep it dramatic but fun.
            
            Mood styles:
            
            Angry:
            Give exaggerated taunts or bold comments.
            Be sarcastic but not abusive.
            
            Sweet:
            Be warm, supportive and soft.
            
            Nonchalant:
            Be casual and slightly indifferent.
            
            Savage:
            Be confident and playful roast.
            
            Motivational:
            Be energetic and uplifting.
            
            Now generate the advice:
            """.formatted(mood, text);

        try {

            // ✅ Proper Gemini JSON structure
            String requestJson = """
                {
                  "contents": [
                    {
                      "parts": [
                        { "text": "%s" }
                      ]
                    }
                  ]
                }
                """.formatted(prompt.replace("\"", "\\\""));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            GeminiResponse gemini =
                    mapper.readValue(response.getBody(), GeminiResponse.class);

            return gemini.candidates.get(0).content.parts.get(0).text;

        } catch (Exception e) {
            e.printStackTrace();
            return "Something dramatic failed. Try again.";
        }
    }
}

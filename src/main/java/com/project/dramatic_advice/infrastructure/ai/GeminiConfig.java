package com.project.dramatic_advice.infrastructure.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Bean
    public GeminiReWriter geminiRewriter() {
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("❌ Gemini API key NOT FOUND");
            return null;
        }
        return new GeminiReWriter(apiKey);
    }
}

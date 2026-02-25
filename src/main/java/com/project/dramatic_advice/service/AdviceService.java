package com.project.dramatic_advice.service;

import com.project.dramatic_advice.domain.generator.TemplateGenerator;
import com.project.dramatic_advice.infrastructure.ai.GeminiReWriter;
import org.springframework.stereotype.Service;

@Service
public class AdviceService {

    private final GeminiReWriter gemini;

    public AdviceService(GeminiReWriter gemini) {
        this.gemini = gemini;
    }

    public String getAdvice(String text) {
        return gemini.rewrite(text);
    }
}
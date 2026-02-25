package com.project.dramatic_advice.application.usecases;

import com.project.dramatic_advice.infrastructure.ai.GeminiReWriter;
import com.project.dramatic_advice.domain.generator.TemplateGenerator;

public class GenerateAdviceUseCase {

    private final TemplateGenerator templateGenerator;
    private final GeminiReWriter aiRewriter;

    public GenerateAdviceUseCase(
            TemplateGenerator templateGenerator,
            GeminiReWriter aiRewriter
    ) {
        this.templateGenerator = templateGenerator;
        this.aiRewriter = aiRewriter;
    }

    public String execute(String input) {
        String baseAdvice = templateGenerator.generate(input);

        try {
            if (aiRewriter != null) {
                return aiRewriter.rewrite(baseAdvice);
            }
        } catch (Exception e) {
            System.out.println("AI failed, falling back to template.");
        }

        return baseAdvice;
    }
}
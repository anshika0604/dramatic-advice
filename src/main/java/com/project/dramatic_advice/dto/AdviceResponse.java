package com.project.dramatic_advice.dto;

public class AdviceResponse {
    private String advice;

    public AdviceResponse(String advice) {
        this.advice = advice;
    }

    public String getAdvice() {
        return advice;
    }
}
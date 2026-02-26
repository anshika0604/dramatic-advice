package com.project.dramatic_advice.dto;

public class AdviceRequest {
    private String text;
    private String mood;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
}

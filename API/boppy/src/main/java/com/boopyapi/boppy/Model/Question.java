package com.boopyapi.boppy.Model;


public class Question {

    private String text;
    // Default constructor
    public Question() {
    }

    // Constructor with parameters (optional)
    public Question(String text) {
        this.text = text;
    }

    // Getter for text
    public String getText() {
        return text;
    }

    // Setter for text
    public void setText(String text) {
        this.text = text;
    }
}
package com.uraniumape.questions;

import com.uraniumape.questions.validation.Validator;

public class Question {
    private final String question;
    private String answer;
    private Validator validator;

    public Question(String question) {
        this.question = question;
    }

    public Question(String question, Validator validator) {
        this.question = question;
        this.validator = validator;
    }

    public void answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public Validator getValidator() {
        return validator;
    }
}

package com.uraniumape.questions.validation;

public interface Validator {
    boolean validate(String input);
    String getError();
}

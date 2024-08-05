package com.uraniumape.questions;

import com.uraniumape.questions.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Questionnaire {

    private UUID playerUUID;
    private int currentQuestion;
    private final List<Question> questions;
    private QuestionnaireAction completeAction;

    public Questionnaire(UUID playerUUID) {
        this.questions = new ArrayList<>();
        this.playerUUID = playerUUID;
    }

    public void ask(String question) {
        questions.add(new Question(question));
    }

    public void ask(String question, Validator validator) {
        questions.add(new Question(question, validator));
    }


    public void setOnComplete(QuestionnaireAction completeAction) {
        this.completeAction = completeAction;
    }

    public Question getCurrentQuestion() {
        if(currentQuestion > questions.size() - 1) {
            return null;
        }

        return questions.get(currentQuestion);
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void answer(String answer) {
        questions.get(currentQuestion).answer(answer);
    }

    public void next() {
        currentQuestion++;
    }

    //TODO: Hide the getValidator(). Maybe results object?
    public List<Question> getResults() {
        return this.questions;
    }

    public void complete() {
        this.completeAction.run(this.getResults());
    }
}

package com.uraniumape.questions;

import com.uraniumape.questions.validation.Sanitizer;
import com.uraniumape.questions.validation.Validator;

import java.util.UUID;

public class QuestionnaireBuilder {
    private Questionnaire questionnaire;

    public QuestionnaireBuilder(UUID playerUUID) {
        this.questionnaire = new Questionnaire(playerUUID);
    }

    public QuestionnaireBuilder ask(String question) {
        questionnaire.ask(question);
        return this;
    }

    public QuestionnaireBuilder ask(String question, Validator validator) {
        questionnaire.ask(question, validator);
        return this;
    }

    public QuestionnaireBuilder onComplete(QuestionnaireAction action) {
        questionnaire.setOnComplete(action);
        return this;
    }

    public Questionnaire build() {
        Questionnaire newQuestionnaire = this.questionnaire;
        this.reset();
        return newQuestionnaire;
    }

    private void reset() {
        this.questionnaire = new Questionnaire(this.questionnaire.getPlayerUUID());
    }
}

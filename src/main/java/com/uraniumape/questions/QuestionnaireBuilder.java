package com.uraniumape.questions;

import com.uraniumape.questions.validation.Validator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class QuestionnaireBuilder {
    private Questionnaire questionnaire;

    public QuestionnaireBuilder(UUID playerUUID, JavaPlugin plugin) {
        this.questionnaire = new Questionnaire(playerUUID, plugin);
    }

    /**
     * @deprecated
     * This usage is no longer the correct way to add a question
     * <p> Use {@link QuestionnaireBuilder#ask(String key, String question)} instead.
     *
     */
    @Deprecated
    public QuestionnaireBuilder ask(String question) {
        questionnaire.ask(question);
        return this;
    }

    /**
     * @deprecated
     * This usage is no longer the correct way to add a question
     * <p> Use {@link QuestionnaireBuilder#ask(String key, String question, Validator validator)} instead.
     *
     */
    @Deprecated
    public QuestionnaireBuilder ask(String question, Validator validator) {
        questionnaire.ask(question, validator);
        return this;
    }

    public QuestionnaireBuilder ask(String key, String question) {
        questionnaire.ask(key, question);
        return this;
    }

    public QuestionnaireBuilder ask(String key, String question, Validator validator) {
        questionnaire.ask(key, question, validator);
        return this;
    }

    public QuestionnaireBuilder setTimeout(int timeout) {
        questionnaire.setTimeout(timeout);
        return this;
    }

    public QuestionnaireBuilder setTimeout(int timeout, String timeoutMessage) {
        questionnaire.setTimeout(timeout, timeoutMessage);
        return this;
    }


    public QuestionnaireBuilder onComplete(QuestionnaireAction action) {
        questionnaire.setOnComplete(action);
        return this;
    }

    public Questionnaire build() {
        return this.questionnaire;
    }

}

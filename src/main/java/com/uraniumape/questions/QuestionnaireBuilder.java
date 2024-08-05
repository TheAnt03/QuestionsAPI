package com.uraniumape.questions;

import com.uraniumape.questions.validation.Validator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class QuestionnaireBuilder {
    private Questionnaire questionnaire;

    public QuestionnaireBuilder(UUID playerUUID, JavaPlugin plugin) {
        this.questionnaire = new Questionnaire(playerUUID, plugin);
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
        return this.questionnaire;
    }

}

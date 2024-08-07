package com.uraniumape.questions;

import com.uraniumape.questions.validation.Validator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Questionnaire {
    private static String DEFAULT_TIMEOUT_MESSAGE = "You've taken too long to respond";
    private final UUID playerUUID;
    private int currentQuestion;
    private final LinkedHashMap<String, Question> questions;
    private QuestionnaireAction completeAction;
    private final JavaPlugin plugin;
    private int timeout;
    private int currentTimeout;
    private String timeoutMessage;

    public Questionnaire(UUID playerUUID, JavaPlugin plugin) {
        this.questions = new LinkedHashMap<>();
        this.playerUUID = playerUUID;
        this.plugin = plugin;
        this.timeout = -1;
        this.timeoutMessage = DEFAULT_TIMEOUT_MESSAGE;
    }

    /**
     * @deprecated
     * This usage is no longer the correct way to add a question
     * <p> Use {@link Questionnaire#ask(String key, String question)} instead.
     *
     */
    @Deprecated
    public void ask(String question) {
        questions.put(String.valueOf(questions.size() - 1), new Question(question));
    }

    /**
     * @deprecated
     * This usage is no longer the correct way to add a question
     * <p> Use {@link Questionnaire#ask(String key, String question, Validator validator)} instead.
     *
     */
    @Deprecated
    public void ask(String question, Validator validator) {
        questions.put(String.valueOf(questions.size() - 1), new Question(question, validator));
    }

    public void ask(String key, String question) {
        questions.put(key, new Question(question));
    }

    public void ask(String key, String question, Validator validator) {
        questions.put(key, new Question(question, validator));
    }

    public void answer(String answer) {
        getQuestionByIndex(currentQuestion).answer(answer);
    }

    public void setOnComplete(QuestionnaireAction completeAction) {
        this.completeAction = completeAction;
    }

    public Question getCurrentQuestion() {
        if(currentQuestion > questions.size() - 1) {
            return null;
        }

        return getQuestionByIndex(currentQuestion);
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void resetTimeout() {
        this.currentTimeout = timeout;
    }

    public void takeTimeoutSecond() {
        currentTimeout = currentTimeout - 1;
    }

    public void setCurrentTimeout(int timeout) {
        currentTimeout = timeout;
    }

    public int getCurrentTimeout() {
        return this.currentTimeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
        this.currentTimeout = timeout;
    }

    public void setTimeout(int timeout, String timeoutMessage) {
        this.timeoutMessage = timeoutMessage;
        setTimeout(timeout);
    }

    public int getTimeout() {
        return this.timeout;
    }

    public String getTimeoutMessage() {
        return this.timeoutMessage;
    }

    public Results getResults() {
        return new Results(questions);
    }

    public void next() {
        currentQuestion++;
    }

    public void complete() {
        Bukkit.getScheduler().runTask(plugin, () -> this.completeAction.run(this.getResults()));
    }

    private Question getQuestionByIndex(int index) {
        String key = (String)questions.keySet().toArray()[index];
        return questions.get(key);
    }
}

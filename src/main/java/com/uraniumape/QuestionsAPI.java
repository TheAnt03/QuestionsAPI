package com.uraniumape;

import com.uraniumape.questions.Questionnaire;
import com.uraniumape.questions.QuestionsManager;
import com.uraniumape.questions.listeners.QuestionnaireListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class QuestionsAPI {
    private final QuestionsManager questionsManager;

    public QuestionsAPI(JavaPlugin plugin) {
        this.questionsManager = new QuestionsManager();
        plugin.getServer().getPluginManager().registerEvents(new QuestionnaireListener(plugin, questionsManager), plugin);
    }

    public void beginQuestionnaire(Questionnaire questionnaire) {
        this.questionsManager.beginQuestionnaire(questionnaire);
    }
}

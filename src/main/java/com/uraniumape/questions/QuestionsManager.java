package com.uraniumape.questions;

import com.uraniumape.questions.validation.Validator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class QuestionsManager {
    private final ConcurrentMap<UUID, Questionnaire> activeQuestionnaires;

    public QuestionsManager() {
        activeQuestionnaires = new ConcurrentHashMap<>();
    }

    public void beginQuestionnaire(Questionnaire questionnaire, JavaPlugin plugin) {
        UUID playerUUID = questionnaire.getPlayerUUID();
        activeQuestionnaires.put(playerUUID, questionnaire);
        Bukkit.getPlayer(playerUUID).sendMessage(questionnaire.getCurrentQuestion().getQuestion());

        if(questionnaire.getTimeout() != -1) {
            startTimeoutCounter(plugin, questionnaire);
        }
    }

    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Questionnaire questionnaire = activeQuestionnaires.get(player.getUniqueId());

        if(questionnaire == null) {
            return;
        }

        Question currentQuestion = questionnaire.getCurrentQuestion();
        String answer = event.getMessage();

        if(!isValidAnswer(currentQuestion, answer)) {
            player.sendMessage(currentQuestion.getValidator().getError());
            player.sendMessage(currentQuestion.getQuestion());
            questionnaire.resetTimeout();
            event.setCancelled(true);
            return;
        }

        questionnaire.answer(event.getMessage());
        questionnaire.next();
        questionnaire.resetTimeout();

        if(isComplete(questionnaire)) {
            questionnaire.complete();

            activeQuestionnaires.remove(player.getUniqueId());
            event.setCancelled(true);
            return;
        }

        player.sendMessage(questionnaire.getCurrentQuestion().getQuestion());
        event.setCancelled(true);
    }

    public void endQuestionnaire(Questionnaire questionnaire) {
        activeQuestionnaires.remove(questionnaire.getPlayerUUID());
        questionnaire.finish();
    }

    public Set<UUID> getActivePlayers() {
        return this.activeQuestionnaires.keySet();
    }


    private boolean isValidAnswer(Question currentQuestion, String answer) {
        Validator validator = currentQuestion.getValidator();
        return validator == null || validator.validate(answer);
    }

    private boolean isComplete(Questionnaire questionnaire) {
        return questionnaire.getCurrentQuestion() == null;
    }

    private void startTimeoutCounter(JavaPlugin plugin, Questionnaire questionnaire) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(questionnaire.getCurrentTimeout() == 0) {
                    Bukkit.getPlayer(questionnaire.getPlayerUUID()).sendMessage(questionnaire.getTimeoutMessage());
                    endQuestionnaire(questionnaire);
                    cancel();
                }

                if(questionnaire.isFinished()) {
                    endQuestionnaire(questionnaire);
                    cancel();
                }

                questionnaire.takeTimeoutSecond();
            }
        }.runTaskTimer(plugin, 0, 20L);
    }
}

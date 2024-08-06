package com.uraniumape.questions.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.uraniumape.questions.Questionnaire;
import com.uraniumape.questions.QuestionsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;

public class QuestionnaireListener implements Listener {
    private JavaPlugin plugin;
    private QuestionsManager manager;

    public QuestionnaireListener(JavaPlugin plugin, QuestionsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    private void removeActivePlayersFromChat(Set<Player> players) {
        for(UUID player : manager.getActivePlayers()) {
            players.remove(Bukkit.getPlayer(player));
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        this.removeActivePlayersFromChat(event.getRecipients());
        manager.onChat(event);
    }
}

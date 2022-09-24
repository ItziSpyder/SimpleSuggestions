package me.itzispyder.simplesuggestions.events;

import me.itzispyder.simplesuggestions.SimpleSuggestions;
import me.itzispyder.simplesuggestions.files.SuggestionFiles;
import me.itzispyder.simplesuggestions.other.Messages;
import me.itzispyder.simplesuggestions.other.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class Notifications implements Listener {


    // instance of the main class
    static SimpleSuggestions plugin;
    public Notifications(SimpleSuggestions plugin) {
        this.plugin = plugin;
    }

    // the events

    @EventHandler
    public static void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (p.isOp()) {
            List<String> entries = SuggestionFiles.getEntries();

            if (entries.size() > 0) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        p.sendMessage(Messages.starter + "2You have §a" + entries.size() + " §2suggestion(s) unread or left on read! Do §a/suggestions!");

                        Sounds.repeating(p,p.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,10,1,3,5);
                    }
                },100);
            }
        }
    }
}

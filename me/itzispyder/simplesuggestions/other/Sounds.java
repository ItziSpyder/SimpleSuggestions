package me.itzispyder.simplesuggestions.other;

import me.itzispyder.simplesuggestions.SimpleSuggestions;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class Sounds implements Listener {


    // instance of the main class
    static SimpleSuggestions plugin;
    public Sounds(SimpleSuggestions plugin) {
        this.plugin = plugin;
    }

    // sounds
    public static void repeating(Player player, Location location, Sound sound, float volume, float pitch, int times, long tickDelay) {
        new BukkitRunnable() {
            int repeatedTimes = 0;

            @Override
            public void run() {
                if (repeatedTimes < times) {
                    player.playSound(location,sound,volume,pitch);
                    repeatedTimes ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,tickDelay);
    }
}

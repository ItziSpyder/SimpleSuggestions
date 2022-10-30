package me.itzispyder.simplesuggestions.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuggestionFiles {
    private static File file;
    private static FileConfiguration suggestions;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SimpleSuggestions").getDataFolder(),"suggestions.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        suggestions = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return suggestions;
    }

    public static void save() {
        try {
            suggestions.save(file);
        } catch (IOException exception) {
            Bukkit.getServer().getLogger().warning("Cannot save file \"suggestions.yml\", check the plugin files for any errors!");
        }
    }

    public static void reload() {
        suggestions = YamlConfiguration.loadConfiguration(file);
    }

    public static List<String> getEntries() {
        try {
            return new ArrayList<>(suggestions.getConfigurationSection("server.suggestions").getKeys(false));
        } catch (NullPointerException exception) {
            return new ArrayList<>();
        }
    }

    public static int getOccupiedPages() {
        double num = getEntries().size() / 21;
        if (getEntries().size() % 21 == 0) {
            return (int) Math.floor(num);
        } else {
            return (int) Math.floor(num) + 1;
        }
    }

    public static boolean willDuplicate(String input) {
        List<String> entries = new ArrayList<>(getEntries());
        return entries.removeIf(key -> !suggestions.getString("server.suggestions.key").contains(input));
    }
}

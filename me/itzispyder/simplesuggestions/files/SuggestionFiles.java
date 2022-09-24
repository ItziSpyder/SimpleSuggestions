package me.itzispyder.simplesuggestions.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
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
        return SuggestionFiles.get().getStringList("server.suggestions.entries");
    }

    public static boolean containsEntry(String string) {
        List<String> entries = getEntries();
        for (String entry : entries) {
            if (entry.equalsIgnoreCase(string)) {
                return true;
            }
        }

        return false;
    }
}

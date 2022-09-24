package me.itzispyder.simplesuggestions;

import me.itzispyder.simplesuggestions.commands.SuggestionCommands;
import me.itzispyder.simplesuggestions.commands.TabCompleters;
import me.itzispyder.simplesuggestions.events.Notifications;
import me.itzispyder.simplesuggestions.events.SuggestionsEvents;
import me.itzispyder.simplesuggestions.files.SuggestionFiles;
import me.itzispyder.simplesuggestions.items.ItemManager;
import me.itzispyder.simplesuggestions.other.Sounds;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleSuggestions extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getLogger().info(" __________________");
        getServer().getLogger().info("|\\     Simple     /|");
        getServer().getLogger().info("|  \\Suggestions /  |     SimpleSuggestions has enabled!");
        getServer().getLogger().info("|  / \\        / \\  |     Author: ItziSpyder");
        getServer().getLogger().info("| /    \\    /    \\ |     Version: v2.0");
        getServer().getLogger().info("|/_______\\/_______\\|");

        // The config file
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        SuggestionFiles.setup();
        SuggestionFiles.get().options().copyDefaults(true);
        SuggestionFiles.save();

        // Register the items
        ItemManager.setExit();
        ItemManager.setBack();
        ItemManager.setBlank();
        ItemManager.setRed();
        ItemManager.setReplies();

        // Server commands
        getCommand("suggest").setExecutor(new SuggestionCommands(this));
        getCommand("suggest").setTabCompleter(new TabCompleters());
        getCommand("suggestions").setExecutor(new SuggestionCommands(this));
        getCommand("suggestions").setTabCompleter(new TabCompleters());
        getCommand("feedback").setExecutor(new SuggestionCommands(this));
        getCommand("feedback").setTabCompleter(new TabCompleters());
        getCommand("/").setExecutor(new SuggestionCommands(this));
        getCommand("simplesuggestions").setExecutor(new SuggestionCommands(this));

        // Server events
        getServer().getPluginManager().registerEvents(new SuggestionsEvents(this), this);
        getServer().getPluginManager().registerEvents(new Notifications(this), this);
        getServer().getPluginManager().registerEvents(new Sounds(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().warning("SimpleSuggestions v2.0 has disabled! If this is not a reload, then consider rebooting!");
    }
}

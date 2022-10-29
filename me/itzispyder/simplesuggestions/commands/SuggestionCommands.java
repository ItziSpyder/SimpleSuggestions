package me.itzispyder.simplesuggestions.commands;

import me.itzispyder.simplesuggestions.SimpleSuggestions;
import me.itzispyder.simplesuggestions.events.SuggestionsEvents;
import me.itzispyder.simplesuggestions.files.SuggestionFiles;
import me.itzispyder.simplesuggestions.other.Messages;
import me.itzispyder.simplesuggestions.other.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuggestionCommands implements CommandExecutor {
    static SimpleSuggestions plugin;
    public SuggestionCommands(SimpleSuggestions plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (command.getName().equals("suggest")) {
                if (args.length >= 1) {
                    String suggestion = "";
                    for (String arg : args) {
                        suggestion += arg + " ";
                    }
                    p.sendMessage(Messages.starter + "2Suggestion submitted, §a/suggestions §2!");
                    Bukkit.getServer().broadcastMessage(Messages.starter + "a" + p.getName() + " §2has just submitted a suggestion! Help out the server by leaving your feedback for us! §a/suggest");
                    Sounds.repeating(p.getPlayer(),p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10,10,3,3);

                    SuggestionFiles.get().set("server.suggestions." + p.getName(), null);
                    SuggestionFiles.get().set("server.suggestions." + p.getName(), suggestion);
                    SuggestionFiles.save();
                } else {
                    p.sendMessage(Messages.starter + "cPlease type your suggestion! You cannot just type /suggest!");
                }

                return true;
            } else if (command.getName().equals("suggestions")) {
                SuggestionsEvents.openSuggestionsMenu(p,SuggestionFiles.getOccupiedPages() - 1);

                return true;
            } else if (command.getName().equals("feedback")) {
                if (p.isOp()) {
                    String feedback = "";
                    for (String arg : args) {
                        feedback += arg + " ";
                    }

                    SuggestionFiles.get().set("server.suggestions.MODERATOR_REPLY-" + SuggestionFiles.getEntries().size(),feedback);
                    SuggestionFiles.save();

                    p.sendMessage(Messages.starter + "2Reply added!");
                } else {
                    p.sendMessage(Messages.starter + "cI'm afraid you do not have the permission to do this!");
                }

                return true;
            } else if (command.getName().equals("simplesuggestions")) {
                Sounds.repeating(p.getPlayer(),p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,10,10,5,5);
                p.sendMessage(
                        "   \n   " + Messages.starter +
                                "\n     §2Version: §av2.0" +
                                "\n     §2Auther: §aItziSpyder" +
                                "\n     §2Developer: §aItziSpyder" +
                                "\n     §2Idea credit: §aZaneTheAEAEAEA" +
                                "\n     §2Description: §aAllows server members to leave feedback!" +
                                "\n     §2Commands: " +
                                "\n     §a- suggest" +
                                "\n     §a- feedback" +
                                "\n     §a- suggestions \n  "
                );

                return true;
            }
        }

        return false;
    }
}

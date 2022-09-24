package me.itzispyder.simplesuggestions.commands;

import me.itzispyder.simplesuggestions.files.SuggestionFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompleters implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> argus = new ArrayList<>();

        switch (command.getName()) {
            case "suggest":
                switch (args.length) {
                    case 1:
                        argus.add("§8§l§o(Type your suggestion!)");
                        break;
                }
                break;
            case "suggestions":
                break;
            case "feedback":
                switch (args.length) {
                    case 1:
                        return SuggestionFiles.getEntries();
                    case 2:
                        argus.add("§8§l§o(Type your feedback to the suggester!)");
                        break;
                }
                break;
        }

        return argus;
    }
}

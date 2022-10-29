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
                        argus.add("§8<your suggestion>");
                        break;
                }
                break;
            case "feedback":
                switch (args.length) {
                    case 1:
                        argus.add("§8<your feedback>");
                        break;
                }
                break;
        }

        return argus;
    }
}

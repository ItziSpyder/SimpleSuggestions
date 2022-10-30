package me.itzispyder.simplesuggestions.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Messages {

    public static String starter = "§8§l>§2§l>§a§l> §";

    public static List<String> autoLoreSplit(String string, int wordsPerLine, String prefix) {
        List<String> lore = new ArrayList<>();
        List<String> words = new ArrayList<>(Arrays.asList(string.split(" ")));
        int lines = (int) Math.ceil(words.size() / (double) wordsPerLine);

        for (int i = 0; i < lines; i ++) {
            StringBuilder builder = new StringBuilder();
            for (int j = (i * wordsPerLine); j < (i * wordsPerLine) + wordsPerLine; j ++ ) {
                try {
                    builder.append(prefix).append(words.get(j)).append(" ");
                } catch (IndexOutOfBoundsException exception) {
                    // empty
                }
            }
            lore.add(String.valueOf(builder));
        }

        return lore;
    }
}

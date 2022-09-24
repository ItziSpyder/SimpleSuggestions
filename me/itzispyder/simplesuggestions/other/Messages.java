package me.itzispyder.simplesuggestions.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Messages {

    public static String starter = "§8[§2Simple§aSuggestions§8] §";

    public static String autoStringSplit(String string, int wordsPerLine) {
        try {
            String[] words = string.split(" ");
            int wordCount = 0;
            String message = "";

            for (String word : words) {
                if (wordCount < wordsPerLine - 1) {
                    message += word + " ";
                    wordCount ++;
                } else if (wordCount < wordsPerLine){
                    message += word + " \n";
                    wordCount = 0;
                }
            }

            return message;
        } catch (IndexOutOfBoundsException exception) {
            return " ";
        }
    }

    public static List<String> autoLoreSplit(String string, int wordsPerLine) {
        List<String> lore = new ArrayList<>();
        try {
            List<String> words = new ArrayList<>(Arrays.asList(string.split(" ")));
            double totalLines = (Math.ceil(words.size()/wordsPerLine) + 1);
            String message = "";
            int wordCount = 0;

            for (String word : words) {
                if (wordCount < wordsPerLine - 1 && totalLines > 1) {
                    message += word + " ";
                    wordCount ++;
                } else if (wordCount < wordsPerLine) {
                    if (totalLines > 1) {
                        message += word + " ";
                        lore.add("§2§o" + message);
                        wordCount = 0;
                        message = "";
                        totalLines --;
                    } else {
                        int wordsLeft = words.size() % wordsPerLine;
                        for (int i = 0; i < wordsLeft; i ++) {
                            message += words.get(words.size() - (wordsLeft - i)) + " ";
                        }
                        lore.add("§2§o" + message);
                        break;
                    }
                }
            }
            return lore;
        } catch (IndexOutOfBoundsException exception) {
            return lore;
        }
    }
}

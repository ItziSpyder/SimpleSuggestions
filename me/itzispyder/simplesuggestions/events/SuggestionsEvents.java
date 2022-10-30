package me.itzispyder.simplesuggestions.events;

import me.itzispyder.simplesuggestions.SimpleSuggestions;
import me.itzispyder.simplesuggestions.files.SuggestionFiles;
import me.itzispyder.simplesuggestions.items.ItemManager;
import me.itzispyder.simplesuggestions.other.Messages;
import me.itzispyder.simplesuggestions.other.Sounds;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.print.Paper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestionsEvents implements Listener {
    static SimpleSuggestions plugin;
    public SuggestionsEvents(SimpleSuggestions plugin) {
        this.plugin = plugin;
    }
    static ItemStack x = ItemManager.blank;
    static ItemStack b = ItemManager.black;
    static ItemStack y = ItemManager.red;
    static ItemStack close = ItemManager.exit;
    static ItemStack back = ItemManager.back;
    static ItemStack previous = ItemManager.previous;
    static ItemStack next = ItemManager.next;
    static ItemStack a = new ItemStack(Material.AIR);
    static ItemStack compass = ItemManager.compass;

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory menu = e.getClickedInventory();

        try {
            String title = e.getView().getTitle();
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String display = meta.getDisplayName();
            List<String> lore = meta.getLore();

            if (title.contains(Messages.starter) && !menu.getType().equals(InventoryType.PLAYER)) {
                e.setCancelled(true);

                if (!display.equalsIgnoreCase(" ")) {
                    if (display.equalsIgnoreCase("§c§lClose Menu")) {
                        p.closeInventory();
                    } else if (display.equalsIgnoreCase("§7§lBack") || display.equalsIgnoreCase("§bTo Current Page")) {
                        openSuggestionsMenu(p,SuggestionFiles.getOccupiedPages() - 1);
                        p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,10,10F);
                    }
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK,10,10);
                }

                if (title.contains(Messages.starter + "eP.")) {
                    if (item.getType().equals(Material.PLAYER_HEAD) || item.getType().equals(Material.PAPER)) {
                        if (p.isOp()) {
                            openSuggestionConfiguration(p,item);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10,0.5F);
                        } else {
                            p.closeInventory();
                            p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT,10,10F);
                            p.sendMessage(Messages.starter + "cSorry but I'm afraid you do not have permission to do this!");
                        }
                    } else {
                        int index = Integer.parseInt(title.substring((Messages.starter + "eP.").length())) - 1;
                        switch (display) {
                            case "Previous Page":
                                if (index > 0) {
                                    openSuggestionsMenu(p,index - 1);
                                    p.playSound(p.getLocation(),Sound.ITEM_BOOK_PAGE_TURN,10,1.5F);
                                }
                                break;
                            case "Next Page":
                                openSuggestionsMenu(p,index + 1);
                                p.playSound(p.getLocation(),Sound.ITEM_BOOK_PAGE_TURN,10,1.5F);
                                break;
                        }
                    }
                } else if (title.contains("§cActions")) {
                    if (display.equalsIgnoreCase("§c§lDelete")) {
                        ItemStack head = menu.getItem(0);
                        List<String> headL = head.getItemMeta().getLore();
                        String recipient = head.getItemMeta().getDisplayName().substring(2);

                        if (head.getType().equals(Material.PAPER)) {
                            int id = Integer.parseInt(headL.get(headL.size() - 1).substring(6));
                            assert headL != null;
                            SuggestionFiles.get().set("server.suggestions.MODERATOR_REPLY-" + id,null);
                        } else {
                            SuggestionFiles.get().set("server.suggestions." + recipient,null);
                        }

                        SuggestionFiles.save();
                        Sounds.repeating(p,p.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,10,0.2F,2,5);
                        p.sendMessage(Messages.starter + "2Deleted one suggestion from §a" + recipient + "!");
                        openSuggestionsMenu(p,SuggestionFiles.getOccupiedPages() - 1);
                    } else if (display.equalsIgnoreCase("§3§lPrint")) {
                        Sounds.repeating(p,p.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,10,10F,3,5);
                        ItemStack head = menu.getItem(0);
                        List<String> headL = head.getItemMeta().getLore();
                        String recipient = head.getItemMeta().getDisplayName().substring(2);
                        TextComponent message = new TextComponent(Messages.starter + "a" + recipient + " suggests:\n"
                                + "§2" + SuggestionFiles.get().getString("server.suggestions." + recipient) + "   §8(Click to copy)");

                        if (head.getType().equals(Material.PAPER)) {
                            int id = Integer.parseInt(headL.get(headL.size() - 1).substring(6));
                            assert headL != null;
                            String path = "server.suggestions.MODERATOR_REPLY-" + id;
                            message = new TextComponent(Messages.starter + "a" + SuggestionFiles.get().getString(path + ".replier") + " says:\n"
                                    + "§2" + SuggestionFiles.get().getString(path + ".text") + "   §8(Click to copy)");
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,String.valueOf(SuggestionFiles.get().getString(path + ".text"))));
                        } else {
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,String.valueOf(SuggestionFiles.get().getString("server.suggestions." + recipient))));
                        }

                        p.closeInventory();
                        p.spigot().sendMessage(message);
                        p.sendMessage();
                    }
                }
            }

        } catch (NullPointerException exception) {
            // empty
        }
    }




    public static void openSuggestionsMenu(Player player,Integer index) {
        Inventory menu = Bukkit.createInventory(player,54, Messages.starter + "eP." + (index + 1));
        List<String> entries = SuggestionFiles.getEntries();
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta bookM = book.getItemMeta();
        bookM.setDisplayName("§aTotal §e" + SuggestionFiles.getEntries().size() + " §acomments!");
        List<String> bookL = new ArrayList<>(Arrays.asList(
                "§e" + SuggestionFiles.getOccupiedPages() + " §aoccupied pages",
                "§7/suggest <your suggestion>"
        ));
        bookM.setLore(bookL);
        book.setItemMeta(bookM);

        ItemStack[] prefill = {
                b,b,b,b,b,b,b,b,b,
                b,a,a,a,a,a,a,a,b,
                b,a,a,a,a,a,a,a,b,
                b,a,a,a,a,a,a,a,b,
                b,b,b,b,b,b,b,b,b,
                previous,y,close,y,book,y,compass,y,next,
        };
        menu.setContents(prefill);

        if (entries.size() != 0) {
            for (int i = index * 21; i < (index * 21 + 21); i ++) {
                try {
                    String entry = SuggestionFiles.getEntries().get(i);
                    ItemStack item = null;

                    if (entry.contains("MODERATOR_REPLY-")) {
                        item = new ItemStack(Material.PAPER);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName("§c§l§nAdmin Reply");
                        String text = SuggestionFiles.get().getString("server.suggestions." + entry + ".text");
                        String replier = SuggestionFiles.get().getString("server.suggestions." + entry + ".replier");
                        int id = SuggestionFiles.get().getInt("server.suggestions." + entry + ".id");
                        String replying_to = SuggestionFiles.get().getString("server.suggestions." + entry + ".replying_to");
                        String replied = SuggestionFiles.get().getString("server.suggestions." + entry + ".replied");
                        List<String> lore = new ArrayList<>();
                        if (replied != null && replying_to != null) {
                            lore.addAll(Messages.autoLoreSplit("\"Replying to " + replied + ": " + replying_to + "\"",5,"§8§o"));
                            lore.add(" ");
                        }
                        lore.add("§7§n" + replier + ": ");
                        assert text != null;
                        lore.addAll(Messages.autoLoreSplit("\"" + text + "\"",5,"§7§o"));
                        lore.add("§0ID: " + id);
                        meta.setLore(lore);
                        meta.addEnchant(Enchantment.LUCK,1,false);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        item.setItemMeta(meta);
                    } else {
                        item = new ItemStack(Material.PLAYER_HEAD);
                        ItemMeta meta = item.getItemMeta();
                        ((SkullMeta) meta).setOwner(entry);
                        meta.setDisplayName("§a" + entry);
                        String suggestion = SuggestionFiles.get().getString("server.suggestions." + entry);
                        assert suggestion != null;
                        meta.setLore(Messages.autoLoreSplit(suggestion,5,"§7§o"));
                        item.setItemMeta(meta);
                    }

                    menu.setItem(menu.firstEmpty(),item);
                } catch (IndexOutOfBoundsException exception) {
                    // empty
                }
            }
        }

        fillEmpty(menu);
        player.openInventory(menu);
    }

    public static void openSuggestionConfiguration(Player player, ItemStack recipient) {
        Inventory menu = Bukkit.createInventory(player,9,Messages.starter + "cActions");

        ItemStack delete = new ItemStack(Material.RED_TERRACOTTA);
        ItemMeta Mdelete = delete.getItemMeta();
        Mdelete.setDisplayName("§c§lDelete");
        delete.setItemMeta(Mdelete);

        ItemStack print = new ItemStack(Material.BLUE_TERRACOTTA);
        ItemMeta Mprint = print.getItemMeta();
        Mprint.setDisplayName("§3§lPrint");
        print.setItemMeta(Mprint);

        ItemStack[] contents = {
                recipient,x,print,x,delete,x,x,back,close
        };

        menu.setContents(contents);
        player.openInventory(menu);
    }

    public static void fillEmpty(Inventory inventory) {
        while (inventory.firstEmpty() != -1) {
            inventory.setItem(inventory.firstEmpty(), ItemManager.blank);
        }
    }
}

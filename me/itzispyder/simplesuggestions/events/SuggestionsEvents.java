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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class SuggestionsEvents implements Listener {
    static SimpleSuggestions plugin;
    public SuggestionsEvents(SimpleSuggestions plugin) {
        this.plugin = plugin;
    }
    static ItemStack x = ItemManager.blank;
    static ItemStack y = ItemManager.red;
    static ItemStack close = ItemManager.exit;
    static ItemStack back = ItemManager.back;
    static ItemStack replies = ItemManager.replies;

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
                    } else if (display.equalsIgnoreCase("§7§lBack")) {
                        openSuggestionsMenu(p);
                    }
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK,10,10);
                }

                if (title.contains("§bSuggestions")) {
                    if (item.getType().equals(Material.PLAYER_HEAD)) {
                        if (p.isOp()) {
                            openSuggestionConfiguration(p,item);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10,0.5F);
                        } else {
                            p.closeInventory();
                            p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT,10,10F);
                            p.sendMessage(Messages.starter + "cSorry but I'm afraid you do not have permission to do this!");
                        }
                    } else if (display.equalsIgnoreCase("§a§lCheck replies")) {
                        openRepliesMenu(p);
                    }
                } else if (title.contains("§cActions")) {
                    if (display.equalsIgnoreCase("§a§lReply")) {
                        p.closeInventory();
                        Sounds.repeating(p,p.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,10,1,2,5);
                        p.sendMessage(Messages.starter + "2Type §a/feedback §2followed along with the recipient and your feedback to them!");
                    } else if (display.equalsIgnoreCase("§c§lDelete")) {
                        ItemStack head = menu.getItem(0);
                        String recipient = head.getItemMeta().getDisplayName().substring(2);
                        Sounds.repeating(p,p.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,10,0.2F,2,5);
                        p.sendMessage(Messages.starter + "2Deleted one suggestion from §a" + recipient + "!");

                        SuggestionFiles.get().set("server.suggestions." + recipient,null);
                        List<String> entries = SuggestionFiles.getEntries();
                        entries.remove(recipient);
                        SuggestionFiles.get().set("server.suggestions.entries",entries);
                        SuggestionFiles.save();
                        openSuggestionsMenu(p);
                    } else if (display.equalsIgnoreCase("§3§lPrint")) {
                        Sounds.repeating(p,p.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,10,10F,3,5);
                        ItemStack head = menu.getItem(0);
                        String recipient = head.getItemMeta().getDisplayName().substring(2);

                        p.closeInventory();
                        TextComponent message = new TextComponent(Messages.starter + "a" + recipient + " suggests:\n"
                                + "§2" + SuggestionFiles.get().getString("server.suggestions." + recipient + ".suggestion") + "   §8(Click to copy)");
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,String.valueOf(SuggestionFiles.get().getString("server.suggestions." + recipient + ".suggestion"))));
                        p.spigot().sendMessage(message);
                        p.sendMessage();
                    }
                }
            }

        } catch (NullPointerException exception) {
            // empty
        }
    }




    public static void openSuggestionsMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player,54, Messages.starter + "bSuggestions");

        List<String> entries = SuggestionFiles.getEntries();

        for (int i = 0; i < 9; i ++) {
            menu.setItem(menu.firstEmpty(), y);
        }

        if (entries.size() != 0) {
            for (String entry : entries) {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta meta = item.getItemMeta();
                ((SkullMeta) meta).setOwner(entry);
                meta.setDisplayName("§a" + entry);
                String suggestion = SuggestionFiles.get().getString("server.suggestions." + entry + ".suggestion");
                meta.setLore(Messages.autoLoreSplit(suggestion,5));

                item.setItemMeta(meta);
                menu.addItem(item);
            }
        } else {
            player.sendMessage(Messages.starter + "cSuggestions is empty!");
        }
        for (int i = 0; i < 9; i ++) {
            menu.setItem(menu.getSize() - (i + 1), y);
        }


        menu.setItem(menu.getSize() - 1, close);
        menu.setItem(menu.getSize() - 9, replies);
        fillEmpty(menu);

        player.openInventory(menu);
    }

    public static void openSuggestionConfiguration(Player player, ItemStack recipient) {
        Inventory menu = Bukkit.createInventory(player,9,Messages.starter + "cActions");

        ItemStack delete = new ItemStack(Material.RED_TERRACOTTA);
        ItemMeta Mdelete = delete.getItemMeta();
        Mdelete.setDisplayName("§c§lDelete");
        delete.setItemMeta(Mdelete);

        ItemStack reply = new ItemStack(Material.LIME_TERRACOTTA);
        ItemMeta Mreply = reply.getItemMeta();
        Mreply.setDisplayName("§a§lReply");
        reply.setItemMeta(Mreply);

        ItemStack print = new ItemStack(Material.BLUE_TERRACOTTA);
        ItemMeta Mprint = print.getItemMeta();
        Mprint.setDisplayName("§3§lPrint");
        print.setItemMeta(Mprint);

        ItemStack[] contents = {
                recipient,reply,print,x,delete,x,x,back,close
        };

        menu.setContents(contents);
        player.openInventory(menu);
    }

    public static void openRepliesMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player,9,Messages.starter + "aReplies");

        String feedback = SuggestionFiles.get().getString("server.suggestions." + player.getName() + ".reply");
        String replier = SuggestionFiles.get().getString("server.suggestions." + player.getName() + ".replier");

        if (feedback != null && replier != null) {
            ItemStack reply = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta Mreply = reply.getItemMeta();
            ((SkullMeta) Mreply).setOwner(player.getName());
            Mreply.setDisplayName("§a" + replier + " replied to your suggestion:");
            Mreply.setLore(Messages.autoLoreSplit(feedback,5));
            reply.setItemMeta(Mreply);

            ItemStack[] contents = {
                    x,x,x,x,reply,x,x,back,close
            };

            menu.setContents(contents);
            player.openInventory(menu);
        } else {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT,10,10F);
            player.sendMessage(Messages.starter + "2Your suggestion has not been read nor replied by a staff member! Please continue to be patient, thank you!");
        }
    }

    public static void fillEmpty(Inventory inventory) {
        while (inventory.firstEmpty() != -1) {
            inventory.setItem(inventory.firstEmpty(), ItemManager.blank);
        }
    }
}

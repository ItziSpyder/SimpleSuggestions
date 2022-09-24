package me.itzispyder.simplesuggestions.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
    public static ItemStack exit;
    public static ItemStack back;
    public static ItemStack blank;
    public static ItemStack red;
    public static ItemStack replies;

    public static void setExit() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§c§lClose Menu");

        item.setItemMeta(meta);
        exit = item;
    }

    public static void setBack() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§7§lBack");

        item.setItemMeta(meta);
        back = item;
    }

    public static void setBlank() {
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        blank = item;
    }

    public static void setRed() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        red = item;
    }

    public static void setReplies() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§a§lCheck replies");

        item.setItemMeta(meta);
        replies = item;
    }
}

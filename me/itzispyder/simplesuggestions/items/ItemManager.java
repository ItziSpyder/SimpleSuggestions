package me.itzispyder.simplesuggestions.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
    public static ItemStack exit;
    public static ItemStack back;
    public static ItemStack blank;
    public static ItemStack black;
    public static ItemStack red;
    public static ItemStack previous;
    public static ItemStack next;
    public static ItemStack compass;

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

    public static void setBlack() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        black = item;
    }

    public static void setRed() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        red = item;
    }

    public static void setPrevious() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Previous Page");

        item.setItemMeta(meta);
        previous = item;
    }

    public static void setNext() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Next Page");

        item.setItemMeta(meta);
        next = item;
    }

    public static void setCompass() {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§bTo Current Page");

        item.setItemMeta(meta);
        compass = item;
    }
}

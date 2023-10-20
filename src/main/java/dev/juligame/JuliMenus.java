package dev.juligame;

import dev.juligame.util.menu.ButtonListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JuliMenus {
    public static JavaPlugin plugin;

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static void start(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ButtonListener(plugin), plugin);
        JuliMenus.plugin = plugin;
    }
}

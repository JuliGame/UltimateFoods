package net.juligame.ultimatefoods;

import dev.juligame.JuliMenus;
import net.juligame.ultimatefoods.classes.Food;
import net.juligame.ultimatefoods.commands.UFCommand;
import net.juligame.ultimatefoods.listeners.IAListener;
import net.juligame.ultimatefoods.listeners.PlayerListener;
import net.juligame.ultimatefoods.listeners.UFItemBridge;
import net.juligame.ultimatefoods.utils.JarFileCopyUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class UltimateFoods extends JavaPlugin {
    public static Config config;
    public static HashMap<String, ArrayList<Food>> categories = new HashMap<>();
    public static UltimateFoods instance;
    public static boolean hasEconomy = false;
    public static Economy economy = null;
    public static boolean UsingItemBridge = false;
    public static void loadAll() {
        categories.clear();
        Food.foods.clear();
        config.load();
        File categoryFolder = new File(instance.getDataFolder().getAbsolutePath() + "/categories");
        if (!categoryFolder.exists()) {
            if (!categoryFolder.mkdir()){
                Bukkit.getLogger().log(Level.SEVERE, "Could not create categories folder!");
                return;
            }
        }
        String[] c = categoryFolder.list(new FilenameFilter() {
            public boolean accept(File current, String name) {
                return (new File(current, name)).isDirectory();
            }
        });

        if (c == null || c.length == 0) {
            try {
                JarFileCopyUtil.copyPathFromJar("Categories", categoryFolder.getAbsolutePath());
                Bukkit.getLogger().log(Level.SEVERE, ChatColor.RED + "It seems like there are no categories!");
                Bukkit.getLogger().log(Level.SEVERE, ChatColor.GREEN + "Defaults are being created!");
            } catch (Exception e){
                Bukkit.getLogger().log(Level.SEVERE, "Could not copy default categories!");
                Bukkit.getLogger().log(Level.SEVERE, "Please contact the developer via the official discord!");
                e.printStackTrace();
            }
            return;
        }
        for (String name : c) {
            File category = new File(categoryFolder, name);

            ArrayList<Food> foods = new ArrayList<>();
            for (File json : category.listFiles()) {
                if (json.getName().endsWith(".json")) {
                    String suffix = json.getName().replace(".json", "").replace("Food_", "");
                    try {
                        Food food = new Food(category.getAbsolutePath(), suffix);
                        food.load();
                        food.initialize();
                        foods.add(food);
                    }
                    catch (Exception e) {
                        Bukkit.getLogger().log(Level.SEVERE, "Could not load food " + suffix + " in category " + name + "!");
                    }
                }
            }
            categories.put(category.getName(), foods);
        }

        if (!UsingItemsAdders){
            for (Food food : Food.foods.values()) {
                food.RegisterRecipe();
            }
        }else {
            try {
                Bukkit.getPluginManager().registerEvents(new IAListener(), instance);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not register IAListener!");
                Bukkit.getLogger().log(Level.SEVERE, "Please contact the developer via the official discord!");
                e.printStackTrace();
            }
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return (economy != null);
    }

    public static boolean UsingItemsAdders = false;
    public void onEnable() {
        if (!(new File(getDataFolder().getAbsolutePath())).exists()) {
            (new File(getDataFolder().getAbsolutePath())).mkdir();
        }
        config = new Config(getDataFolder().getAbsolutePath());
        config.load();
        instance = this;
        loadAll();

        Plugin itemsAdders = Bukkit.getPluginManager().getPlugin("ItemsAdder");
        UsingItemsAdders = itemsAdders != null;


        UsingItemBridge = Bukkit.getPluginManager().getPlugin("ItemBridge") != null;
        if (UsingItemBridge)
            new UFItemBridge(this);


        for (Map.Entry<String, Food> entry : Food.foods.entrySet()) {
            Food food = entry.getValue();

            if (food.recipe.getCraft_amount() == 0) {
                food.recipe.setCraft_amount(1);
                food.save();
            }
        }
        hasEconomy = setupEconomy();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        UFCommand ufc = new UFCommand();
        getCommand("ultimatefood").setExecutor(ufc);
        getCommand("ultimatefoods").setExecutor(ufc);
        getCommand("uf").setExecutor(ufc);

        getCommand("ultimatefood").setTabCompleter(ufc);
        getCommand("ultimatefoods").setTabCompleter(ufc);
        getCommand("uf").setTabCompleter(ufc);

        JuliMenus.start(this);
    }

    public void onDisable() {
    }
}
package net.juligame.ultimatefoods.commands;

import dev.juligame.premade.EditorMenu;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import net.juligame.ultimatefoods.classes.Recipe;
import net.juligame.ultimatefoods.menus.CategoriesMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UFCommand
        implements CommandExecutor, TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("reload");
            list.add("get");
            list.add("create");
            list.add("help");
            list.add("menu");
            list.add("edit");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("edit"))
                Food.foods.forEach((uid, food) -> {
                    if (args[1].length() == 0) {
                        list.add(uid.replace(" ", "_"));
                    } else if (uid.toLowerCase().replace(" ", "_").startsWith(args[1].toLowerCase().replace(" ", "_"))) {
                        list.add(uid.replace(" ", "_"));
                    }
                });
            if (args[0].equalsIgnoreCase("create"))
                UltimateFoods.categories.forEach((uid, category) -> {
                    if (args[1].length() == 0) {
                        list.add(uid.replace(" ", "_"));
                    } else if (uid.toLowerCase().replace(" ", "_").startsWith(args[1].toLowerCase().replace(" ", "_"))) {
                        list.add(uid.replace(" ", "_"));
                    }
                });
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {
                list.add("test_name");
            }
            if (args[0].equalsIgnoreCase("get")) {
                if (Food.foods.containsKey(args[1])) {
                    list.add("1");
                    list.add("2");
                    list.add("4");
                    list.add("8");
                    list.add("16");
                    list.add("32");
                    list.add("64");
                }
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("get")) {
                if (Food.foods.containsKey(args[1])) {
                    for (Player player : sender.getServer().getOnlinePlayers()) {
                        if (args[3].isEmpty()) {
                            list.add(player.getName());
                        } else if (player.getName().toLowerCase().startsWith(args[3].toLowerCase())) {
                            list.add(player.getName());
                        }
                    }
                }
            }
        }

        return list;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|---------------&3[&a&eUltimate&aFoods&3-&61.2.0&3]&8----------------|"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "/&3uf help &8- &dProvides the list of available commands"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "/&3uf create [CategoryName] &8- &dIt creates a category for you to add food"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "/&3uf get [FoodID] &8- &dGives you a food according to the specified ID"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "/&3uf menu &8- &dIt will open a menu where you can search for food by category"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "/&3uf reload &8- &dIt will reload the whole plugin and its configuration."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8|-------------------------------------------------|"));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("uf.reload")) {
            sender.sendMessage("Reloading...");
            UltimateFoods.loadAll();
            return true;
        }
        if (args[0].equalsIgnoreCase("get") && sender.hasPermission("uf.get")) {
            if (args.length == 1) {
                sender.sendMessage("Usage: /uf get <food> <ammount> <player>");
                return true;
            }
            if (args.length == 2 || args.length == 3) {
                if (Food.foods.containsKey(args[1])) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("You must be a player to execute this command!");
                        return false;
                    }
                    Player player = (Player) sender;

                    Food food = Food.foods.get(args[1]);

                    if (args.length == 3) {
                        try {
                            int amount = Integer.parseInt(args[2]);
                            ItemStack itemStack = food.getItemStack().clone();
                            itemStack.setAmount(amount);
                            player.getInventory().addItem(itemStack);
                        } catch (NumberFormatException e) {
                            player.getInventory().addItem(food.getItemStack());
                        }
                        return true;
                    }
                    player.getInventory().addItem(food.getItemStack());

                    return true;
                }
            }
            if (args.length == 4) {
                if (Food.foods.containsKey(args[1])) {
                    Food food = Food.foods.get(args[1]);

                    Player target = Bukkit.getServer().getPlayer(args[3]);
                    if (target == null) {
                        sender.sendMessage("Player not found!");
                        return true;
                    }

                    try {
                        int amount = Integer.parseInt(args[2]);
                        ItemStack itemStack = food.getItemStack().clone();
                        itemStack.setAmount(amount);
                        target.getInventory().addItem(itemStack);
                    } catch (NumberFormatException e) {
                        target.getInventory().addItem(food.getItemStack());
                    }
                    return true;
                }
            }

            sender.sendMessage("Food not found!");
            return true;
        }
        if (args[0].equalsIgnoreCase("create") && sender.hasPermission("uf.create")) {
            if (args.length == 1) {
                sender.sendMessage("Usage: /uf create <category>");
                return true;
            }
            if (args.length == 2) {
                sender.sendMessage("Usage: /uf create <category> <name>");
                return true;
            }
            if (!UltimateFoods.categories.containsKey(args[1])) {

                sender.sendMessage("Category not found! Creating...");
                File folder = new File(UltimateFoods.instance.getDataFolder().getAbsolutePath() + "/categories/" + args[1]);
                if (!folder.exists()) folder.mkdirs();
                UltimateFoods.categories.put(args[1], new ArrayList());
            }
            Food food = new Food(UltimateFoods.instance.getDataFolder().getAbsolutePath() + "/categories/" + args[1], args[2]);
            food.create(args[2], "Default Food", false, new ArrayList(), "APPLE", 0, 0.0F, 0.0F, 0.0F, new ArrayList(), new Recipe(new String[]{"xxx", "xxx", "xxx"}, new HashMap<>()), "", 0.0D);

            food.save();
            sender.sendMessage("Food created! To edit it, go to " + UltimateFoods.instance.getDataFolder().getAbsolutePath() + "/categories/" + args[1] + "/" + args[2] + ".yml");
            sender.sendMessage("Remember to reload the plugin (/uf reload) to see the new changes!");
            return true;
        }
        if (args[0].equalsIgnoreCase("menu") && sender.hasPermission("uf.menu")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to execute this command!");
                return false;
            }
            Player player = (Player) sender;

            (new CategoriesMenu()).openMenu(player);
            return true;
        }
        if (args[0].equalsIgnoreCase("edit") && sender.hasPermission("uf.edit")) {
            if (args.length == 1) {
                sender.sendMessage("Usage: /uf edit <food>");
                return true;
            }
            if (Food.foods.containsKey(args[1])) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("You must be a player to execute this command!");
                    return false;
                }
                Player player = (Player) sender;

                Food food = Food.foods.get(args[1]);
                (new EditorMenu(food, null, null, () -> {
                    food.save();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFood saved successfully"));
                    return null;
                })).openMenu(player);
                return true;
            }
            sender.sendMessage("Food not found!");
            return true;
        }

        return false;
    }
}
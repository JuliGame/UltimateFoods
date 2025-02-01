package net.juligame.ultimatefoods.menus;

import dev.juligame.premade.EditorMenu;
import dev.juligame.util.menu.Button;
import dev.juligame.util.menu.Menu;
import dev.juligame.util.menu.layeredMenu.LayeredMenu;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FoodsMenu extends LayeredMenu {
    String category;
    ArrayList<Food> foods;
    Menu lastmenu;

    public FoodsMenu(String category, ArrayList<Food> foods, Menu lastMenu) {
        this.category = category;
        this.foods = foods;
        this.lastmenu = lastMenu;
    }


    public List<Button> getLayeredButtons(Player player) {
        List<Button> botones = new ArrayList<>();
        final FoodsMenu menu = this;
        for (Food food : this.foods) {
            botones.add(new Button() {
                public ItemStack getButtonItem(Player player) {
                    ItemStack item = food.getItemStack();
                    ItemMeta meta = item.getItemMeta();

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7" + meta.getDisplayName()));

                    ArrayList<String> lore = new ArrayList<>();

                    lore.add(ChatColor.translateAlternateColorCodes('&', "&8➟ &eRight click to see the recipe"));
                    if (player.hasPermission("uf.get")) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&8➟ &6Left click to get"));
                    } else {
                        lore.add(ChatColor.translateAlternateColorCodes('&', "§7Left click to buy for: " + food.price));
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    return item;
                }


                public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                    if (clickType.isShiftClick() && clickType.isRightClick() && player.hasPermission("uf.edit")) {
                        (new EditorMenu(food, menu, null, () -> {
                            food.save();
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFood saved successfully"));
                            return null;
                        })).openMenu(player);
                        return;
                    }
                    if (clickType.isRightClick() && food.recipe != null) {
                        if (player.hasPermission("uf.edit.op"))
                            (new RecipeEdit(food.recipe, food.getItemStack(), menu, food)).openMenu(player);
                        else
                            new RecipeShow(food.recipe, food.getItemStack(), menu).openMenu(player);
                        return;
                    }
                    if (player.hasPermission("uf.get")) {
                        player.getInventory().addItem(new ItemStack[]{food.getItemStack()});
                        return;
                    }
                    if (UltimateFoods.hasEconomy) {
                        if (food.price != 0.0D && UltimateFoods.economy.getBalance(player) >= food.price) {
                            UltimateFoods.economy.withdrawPlayer(player, food.price);
                            player.getInventory().addItem(new ItemStack[]{food.getItemStack()});
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have enough money to buy this item"));
                        }
                    }
                }
            });
        }

        return botones;
    }


    public int getPageRows() {
        return 4;
    }


    public String getLayeredTitle(Player player) {
        return this.category;
    }


    public Menu getLastMenu() {
        return this.lastmenu;
    }
}

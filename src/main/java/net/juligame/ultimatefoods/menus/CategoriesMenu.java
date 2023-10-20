package net.juligame.ultimatefoods.menus;

import dev.juligame.util.menu.Button;
import dev.juligame.util.menu.pagination.PaginatedMenu;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoriesMenu extends PaginatedMenu {
    public String getPrePaginatedTitle(Player player) {
        return ChatColor.WHITE + "Categories";
    }


    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> botones = new HashMap<>();
        final CategoriesMenu menu = this;
        int i = 0;
        for (Map.Entry<String, ArrayList<Food>> set : UltimateFoods.categories.entrySet()) {
            final String category = set.getKey();
            final ArrayList<Food> foods = set.getValue();

            botones.put(Integer.valueOf(i), new Button() {
                public ItemStack getButtonItem(Player player) {
                    ItemStack item = foods.get(0).getItemStack();
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7" + category));
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&8➟ &eLeft click to open"));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    return item;
                }


                public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                    (new FoodsMenu(category, foods, menu)).openMenu(player);
                }
            });
            i++;
        }
        return botones;
    }
}
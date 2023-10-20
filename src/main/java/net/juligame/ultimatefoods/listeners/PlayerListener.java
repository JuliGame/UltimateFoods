package net.juligame.ultimatefoods.listeners;

import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerListener implements Listener {
    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (UltimateFoods.config.sendResourcePack)
            player.setResourcePack(UltimateFoods.config.resourcePackURL);
    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        ItemStack itemStack = event.getItem().clone();

        Player player = event.getPlayer();

        Food food = GetFoodFromItemStack(itemStack);
        if (food == null) {
            return;
        }
        if (!food.permission.isEmpty() && !player.hasPermission(food.permission)) {
            return;
        }
        food.ApplyEffect(player);
        event.setCancelled(true);
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.getInventory().setItemInMainHand(itemStack);
        } else if (player.getInventory().getItemInMainHand().equals(itemStack)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        } else if (player.getInventory().getItemInOffHand().equals(itemStack)) {
            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        } else {
            System.out.println("Error: Item not in inventory.");
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null) {
            return;
        }
        Player player = event.getPlayer();
        Food food = GetFoodFromItemStack(itemStack);
        if (food == null) {
            return;
        }
        if (itemStack.getType().isEdible() &&
                !food.instantEat) {
            return;
        }

        if (!food.permission.isEmpty() && !player.hasPermission(food.permission)) {
            return;
        }
        food.ApplyEffect(player);
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else if (player.getInventory().getItemInMainHand().equals(itemStack)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        } else if (player.getInventory().getItemInOffHand().equals(itemStack)) {
            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        } else {
            System.out.println("Error: Item not in inventory.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        if (itemStack == null) {
            return;
        }
        Player player = event.getPlayer();
        Food food = GetFoodFromItemStack(itemStack);
        if (food == null) {
            return;
        }
        if (!food.permission.isEmpty() && !player.hasPermission(food.permission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Recipe recipe = event.getRecipe();
        if (Food.recipes.isEmpty())
            return;

        NamespacedKey thisNSK = ((ShapedRecipe) Food.recipes.get(0)).getKey();
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;

            if (thisNSK.getNamespace().equals(shapedRecipe.getKey().getNamespace())) {
                return;
            }
        }

        ItemStack[] itemStack = event.getInventory().getMatrix();
        for (ItemStack item : itemStack) {
            if (item == null)
                continue;

            Food food = GetFoodFromItemStack(item);
            if (food == null)
                continue;

            event.setCancelled(true);
            return;
        }

    }
    public static Food GetFoodFromItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        if (!itemStack.hasItemMeta()) {
            return null;
        }

        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();

        if (!persistentDataContainer.has(new NamespacedKey("ultimatefood", "modifiers"), PersistentDataType.STRING)) {
            return null;
        }
        String itemID = persistentDataContainer.get(new NamespacedKey("ultimatefood", "modifiers"), PersistentDataType.STRING);

        return Food.foods.get(itemID);
    }
}

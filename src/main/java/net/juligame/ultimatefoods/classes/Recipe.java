package net.juligame.ultimatefoods.classes;/*    */

import net.juligame.ultimatefoods.UltimateFoods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class Recipe {
    private final String[] shape;
    private final HashMap<Character, String> materials;
    private int craft_amount;

    public Recipe(String[] shape, HashMap<Character, String> ingredients) {
        this.shape = shape;
        this.materials = ingredients;
    }

    public int getCraft_amount() {
        return this.craft_amount;
    }

    public void setCraft_amount(int craft_amount) {
        this.craft_amount = craft_amount;
    }

    public String[] getShape() {
        return this.shape;
    }

    public HashMap<Character, String> getMaterials() {
        return this.materials;
    }

    public boolean isValid() {
        for (Map.Entry<Character, String> entry : this.materials.entrySet()) {
            if (!entry.getValue().equalsIgnoreCase("air") && (
                    this.shape[0].toLowerCase().contains(entry.getKey().toString().toLowerCase()) || this.shape[1]
                            .toLowerCase().contains(entry.getKey().toString().toLowerCase()) || this.shape[2]
                            .toLowerCase().contains(entry.getKey().toString().toLowerCase()))) {
                return true;
            }
        }

        return false;
    }

    public static void removeRecipe(ItemStack itemStack){
        ItemStack itemToRemove = null;
        if (itemStack == null || itemStack.getType().equals(Material.AIR))
            return;

        List<org.bukkit.inventory.Recipe> recipes = UltimateFoods.instance.getServer().getRecipesFor(itemStack);

        for (org.bukkit.inventory.Recipe recipe : recipes) {
            if (recipe == null)
                continue;

            if(recipe.getResult().equals(itemStack)){
                itemToRemove = recipe.getResult();
                break;
            }
        }

        if (itemToRemove != null) {
            Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.getServer().recipeIterator();
            while (iterator.hasNext()) {
                org.bukkit.inventory.Recipe recipe = iterator.next();
                if (recipe != null && recipe.getResult().equals(itemToRemove)) {
                    iterator.remove();
                }
            }
        } else {
            Bukkit.getLogger().info("Item to remove not found");
        }
    }
}

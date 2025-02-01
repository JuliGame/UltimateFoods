package net.juligame.ultimatefoods.menus;

import dev.juligame.util.menu.Button;
import dev.juligame.util.menu.Menu;
import dev.juligame.util.menu.backableMenu.BackableMenu;
import dev.lone.itemsadder.api.CustomStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import net.juligame.ultimatefoods.classes.Recipe;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RecipeShow extends BackableMenu {
    String[] shape;

    Map<Character, String> materials;

    ItemStack itemStack;

    Menu menu;

    public RecipeShow(Recipe recipe, ItemStack itemStack, Menu menu) {
        this.shape = recipe.getShape();
        this.materials = recipe.getMaterials();
        this.itemStack = itemStack;
        this.menu = menu;
    }

    public String getTitle(Player player) {
        return "Recipe";
    }

    public List<Button> getBackableMenuButtons(Player player) {
        List<Button> botones = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            botones.add(new Button() {
                public ItemStack getButtonItem(Player player) {
                    return new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                }
            });
        }
        botones.add(2, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[0].charAt(0))));
            }
        });
        botones.add(3, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[0].charAt(1))));
            }
        });
        botones.add(4, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[0].charAt(2))));
            }
        });
        botones.add(11, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[1].charAt(0))));
            }
        });
        botones.add(12, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[1].charAt(1))));
            }
        });
        botones.add(13, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[1].charAt(2))));
            }
        });
        botones.add(15, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.itemStack;
            }
        });
        botones.add(20, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[2].charAt(0))));
            }
        });
        botones.add(21, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[2].charAt(1))));
            }
        });
        botones.add(22, new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(RecipeShow.this.materials.get(Character.valueOf(RecipeShow.this.shape[2].charAt(2))));
            }
        });
        return botones;
    }

    public Menu getLastMenu() {
        return this.menu;
    }

    ItemStack getItemStack(String material) {
        Material m = Material.getMaterial(material);
        if (m != null)
            return new ItemStack(m);
        Food food = (Food)Food.foods.get(material);
        if (food != null)
            return food.getItemStack();
        if (UltimateFoods.UsingItemsAdders) {
            CustomStack stack = CustomStack.getInstance(material);
            if (stack != null)
                return stack.getItemStack();
        }
        return new ItemStack(Material.BARRIER);
    }
}

package net.juligame.ultimatefoods.menus;

import com.jojodmo.itembridge.ItemBridge;
import dev.juligame.util.menu.Button;
import dev.juligame.util.menu.Menu;
import dev.juligame.util.menu.backableMenu.BackableMenu;
import dev.lone.itemsadder.api.CustomStack;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import net.juligame.ultimatefoods.classes.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.juligame.ultimatefoods.classes.Food.foods;
import static net.juligame.ultimatefoods.listeners.PlayerListener.GetFoodFromItemStack;

public class RecipeShow
        extends BackableMenu {
    String[] shape;
    Map<Character, String> materials;
    ItemStack itemStack;
    Menu menu;
    boolean op;
    String Ishape[][];
    Food food;

    public RecipeShow(Recipe recipe, ItemStack itemStack, Menu menu, boolean op, Food food) {
        this.shape = recipe.getShape();
        this.materials = recipe.getMaterials();
        this.itemStack = itemStack;
        this.menu = menu;
        this.op = op;
        Ishape = new String[][]{
                {RecipeShow.this.materials.get(RecipeShow.this.shape[0].charAt(0)),
                        RecipeShow.this.materials.get(RecipeShow.this.shape[0].charAt(1)),
                        RecipeShow.this.materials.get(RecipeShow.this.shape[0].charAt(2))},
                {RecipeShow.this.materials.get(RecipeShow.this.shape[1].charAt(0)),
                        RecipeShow.this.materials.get(RecipeShow.this.shape[1].charAt(1)),
                        RecipeShow.this.materials.get(RecipeShow.this.shape[1].charAt(2))},
                {RecipeShow.this.materials.get(RecipeShow.this.shape[2].charAt(0)),
                        RecipeShow.this.materials.get(RecipeShow.this.shape[2].charAt(1)),
                        RecipeShow.this.materials.get(RecipeShow.this.shape[2].charAt(2))},
        };
        this.food = food;
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
        botones.add(2, getButton(0, 0));
        botones.add(3, getButton(0, 1));
        botones.add(4, getButton(0, 2));
        botones.add(11,getButton(1, 0));
        botones.add(12,getButton(1, 1));
        botones.add(13,getButton(1, 2));
        botones.add(19,getButton(2, 0));
        botones.add(20,getButton(2, 1));
        botones.add(21,getButton(2, 2));

        botones.add(15,new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.itemStack;
            }
        });
        return botones;
    }

    Button getButton(int x, int y){
        return new Button() {
            public ItemStack getButtonItem(Player player) {
                return RecipeShow.this.getItemStack(Ishape[x][y]);
            }
            @Override
            public boolean shouldCancel(Player player, int slot, ClickType clickType) {
//                return true;
                return !op;
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
//                return true;
                return false;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                return;
            }
        };
    }

    @Override
    public boolean isUpdateAfterClick() {
        return false;
    }

    public Menu getLastMenu() {
        return this.menu;
    }

    ItemStack getItemStack(String material) {
        Material m = Material.getMaterial(material);
        if (m != null) {
            return new ItemStack(m);
        }

        Food food = foods.get(material);
        if (food != null) {
            return food.getItemStack();
        }


        if (UltimateFoods.UsingItemsAdders) {
            CustomStack stack = CustomStack.getInstance(material);
            if (stack != null) {
                return stack.getItemStack();
            }
        }
        return new ItemStack(Material.BARRIER);
    }

    public static String getString(ItemStack is) {
        String item = is.getType().toString();

        Food food = GetFoodFromItemStack(is);
        if (food != null) {
            item = food.uniqueID;
        }

        if (UltimateFoods.UsingItemsAdders && CustomStack.byItemStack(is) != null)
            item = CustomStack.byItemStack(is).getNamespacedID();

        if (UltimateFoods.UsingItemBridge && ItemBridge.getItemKey(is) != null && !ItemBridge.getItemKey(is).getNamespace().equals("minecraft"))
            item = ItemBridge.getItemKey(is).toString();

        return item;
    }

    @Override
    public void onClose(Player player) {
        char[] shape = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        HashMap<Character, String> map = new HashMap<>();
        String[] newShape = new String[]{"", "" , ""};
        for (int i = 0; i < 9; i++) {
            int y = i / 3;
            int x = i % 3;
            ItemStack[] contents = player.getOpenInventory().getTopInventory().getContents();

            int g = x+2 + y*9;
            String material;
            if (contents[g] == null) {
                material = "AIR";
            } else {
                material = getString(contents[g]);
            }
            char c;
            if (!map.containsValue(material)) {
                c = shape[i];
                map.put(c, material);
            } else {
                c = map.entrySet().stream().filter(entry -> entry.getValue().equals(material)).findFirst().get().getKey();
            }
            newShape[i / 3] += c;
        }
        for (int i = 0; i < 3; i++) {
            newShape[i] = newShape[i].trim();
        }

        Recipe recipe = new Recipe(newShape, map);
        food.recipe = recipe;
        food.save();
        player.sendMessage("§aRecipe " + food.uniqueID + " saved!");
        if (food.addRecipe())
            player.sendMessage("§aRecipe " + food.uniqueID + " added correctly!");
        else
            player.sendMessage("§cRecipe " + food.uniqueID + " not added correctly!");

        super.onClose(player);
    }
}


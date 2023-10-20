package net.juligame.ultimatefoods.classes;

import dev.juligame.Jconfig.JConfig;
import dev.juligame.Jconfig.NotSerialize;
import dev.juligame.util.menu.Button;
import dev.lone.itemsadder.api.CustomStack;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.utils.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.logging.Level;

public class Food extends JConfig {
    public static HashMap<String, Food> foods = new HashMap<>();

    public String uniqueID;

    public String displayName;

    public ArrayList<String> description;

    public boolean instantEat;

    public String foodMaterial;

    public String headTexture;
    public int customModelData;
    public String permission;
    public double price;
    public float saturation;
    public float hunger;
    public float health;
    public ArrayList<String> commands;
    public net.juligame.ultimatefoods.classes.Recipe recipe;

    public Food(String path, String suffix) {
        super(path, suffix);
    }

    public void create(String uniqueID, String displayName, boolean instantEat, ArrayList<String> description, String material, int customModelData, float saturation, float hunger, float health, ArrayList<String> commands, net.juligame.ultimatefoods.classes.Recipe recipe, String permission, double price) {
        this.uniqueID = uniqueID;
        this.displayName = displayName;
        this.instantEat = instantEat;
        this.description = description;
        this.foodMaterial = material;
        this.customModelData = customModelData;
        this.saturation = saturation;
        this.hunger = hunger;
        this.health = health;
        this.commands = commands;
        this.recipe = recipe;
        this.permission = permission;
        this.price = price;
        this.headTexture = "";
        initialize();
    }


    public void initialize() {
        if (this.uniqueID != null) foods.put(this.uniqueID, this);
        foodMaterial = foodMaterial.toUpperCase();
    }

    public static List<org.bukkit.inventory.Recipe> recipes = new ArrayList<>();
    public void RegisterRecipe() {
        if (this.recipe != null && this.recipe.isValid()) {
            addRecipe();
        };
    }

    public ItemStack getItemStack() {
        Material material = Material.getMaterial(this.foodMaterial);
        ItemStack itemStack;

        boolean isItemsAdder = false;

        if (material != null) {
            itemStack = new ItemStack(material);

            if (material == Material.PLAYER_HEAD) {
                if (this.headTexture == null || this.headTexture.isEmpty()) {
                    this.headTexture = "MHF_GitHub";
                    save();
                }

                UUID uuid = null;
                try {
                    uuid = UUID.fromString(this.headTexture);
                }catch (IllegalArgumentException ignored) {}
                if (uuid != null){
                    UltimateFoods.instance.getLogger().log(Level.SEVERE, "Please don't use UUIDs for head textures. Use base64 or player name instead.");
                }

                else {
                    try {
                        SkullCreator.itemWithBase64(itemStack, this.headTexture);
                    } catch (Exception e) {
                        try {
                            SkullCreator.itemWithName(itemStack, this.headTexture);
                        } catch (Exception ignored) {
                            UltimateFoods.instance.getLogger().log(Level.SEVERE, "Error while creating head texture for " + this.uniqueID + " (" + this.displayName + ")");
                        }
                    }
                }
            }
        } else {
            if (!UltimateFoods.UsingItemsAdders) {
                UltimateFoods.instance.getLogger().log(Level.SEVERE, "Error while creating itemstack for " + this.uniqueID + " (" + this.displayName + ")");
                return new ItemStack(Material.APPLE);
            }

            CustomStack stack = CustomStack.getInstance(foodMaterial);
            if (stack != null) {
                itemStack = stack.getItemStack();
                isItemsAdder = true;
            } else {
                UltimateFoods.instance.getLogger().log(Level.SEVERE, "Error while creating itemstack for " + this.uniqueID + " (" + this.displayName + ")");
                UltimateFoods.instance.getLogger().log(Level.SEVERE, "Material is not a minecraft material or an ItemsAdders material!");
                return null;
            }
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!isItemsAdder){
            itemStack.setAmount(1);
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            itemMeta.setCustomModelData(Integer.valueOf(this.customModelData));

            List<String> newDesc = (List<String>) description.clone();
            newDesc.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
            itemMeta.setLore(newDesc);
        }


        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey("ultimatefood", "modifiers"), PersistentDataType.STRING, this.uniqueID);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void ApplyEffect(Player p) {
        p.setSaturation(p.getSaturation() + this.saturation);
        p.setFoodLevel((int) (p.getFoodLevel() + this.hunger));

        double finalHealth = p.getHealth() + this.health;

        if (finalHealth > p.getMaxHealth()) {
            finalHealth = p.getMaxHealth();
        }
        p.setHealth(finalHealth);

        boolean wasOp = p.isOp();
        try {
            p.setOp(true);
            for (String command : this.commands) {
                p.performCommand(command.replace("%player%", p.getName()));
            }
            p.setOp(wasOp);
        } catch (Exception e) {
            p.sendMessage("§cAn error occurred while executing the command!");
        }
    }

    @NotSerialize
    private ShapedRecipe currentRecipe = null;
    public boolean addRecipe() {
        if (this.recipe == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while creating recipe for " + this.uniqueID + " recipe is null!");
            return false;
        }


        Recipe.removeRecipe(getItemStack());
        if (currentRecipe != null){
            recipes.remove(currentRecipe);
        }

        ItemStack is = getItemStack().clone();
        if (this.recipe.getCraft_amount() == 0)
            this.recipe.setCraft_amount(1);
        else if (this.recipe.getCraft_amount() < 0)
            return false;

        is.setAmount(this.recipe.getCraft_amount());

        if (is.getType().equals(Material.AIR) || is.getAmount() == 0)
            return false;

        try {
            if (currentRecipe == null)
                currentRecipe = new ShapedRecipe(new NamespacedKey(UltimateFoods.instance, this.uniqueID.replace(" ", "_")), is);

            currentRecipe.shape(this.recipe.getShape());
            for (Character key : this.recipe.getMaterials().keySet()) {
                Material m = Material.getMaterial(this.recipe.getMaterials().get(key).toUpperCase());
                if (m != null) {
                    RecipeChoice.MaterialChoice materialChoice = new RecipeChoice.MaterialChoice(m);
                    currentRecipe.setIngredient(key.charValue(), materialChoice);
                    continue;
                }

                Food food = foods.get(this.recipe.getMaterials().get(key));
                if (food != null) {
                    RecipeChoice.ExactChoice exactChoice = new RecipeChoice.ExactChoice(food.getItemStack());
                    currentRecipe.setIngredient(key, exactChoice);
                    continue;
                }

                if (UltimateFoods.UsingItemsAdders) {
                    CustomStack stack = CustomStack.getInstance(key.toString());
                    if (stack != null) {
                        RecipeChoice.ExactChoice exactChoice = new RecipeChoice.ExactChoice(stack.getItemStack());
                        currentRecipe.setIngredient(key.charValue(), exactChoice);
                        continue;
                    }
                }

                UltimateFoods.instance.getLogger().log(Level.SEVERE, "Error while creating recipe for " + this.uniqueID + " (" + this.displayName + ")");
                UltimateFoods.instance.getLogger().log(Level.SEVERE, key + " Material is not a minecraft material, an ItemsAdders material, or an uf food!");
                return false;
            }

            if (!recipes.contains(currentRecipe)) {
                recipes.add(currentRecipe);
                UltimateFoods.instance.getServer().addRecipe(currentRecipe);
            }


            return true;
        } catch (Exception e) {
            UltimateFoods.instance.getLogger().log(Level.SEVERE, "Error while creating recipe for " + this.uniqueID + " (" + this.displayName + ")");
            UltimateFoods.instance.getLogger().log(Level.SEVERE, "Recipe shape is not valid!");
            e.printStackTrace();
            return false;
        }
    }
}


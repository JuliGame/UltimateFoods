package net.juligame.ultimatefoods.listeners;

import com.jojodmo.itembridge.ItemBridge;
import com.jojodmo.itembridge.ItemBridgeListener;
import com.jojodmo.itembridge.ItemBridgeListenerPriority;
import net.juligame.ultimatefoods.UltimateFoods;
import net.juligame.ultimatefoods.classes.Food;
import org.bukkit.inventory.ItemStack;

public class UFItemBridge implements ItemBridgeListener {
    public UFItemBridge(UltimateFoods plugin){
        ItemBridge bridge = new ItemBridge(plugin, "ultimatefoods", "uf");
        bridge.registerListener(this);
    }

    @Override
    public ItemBridgeListenerPriority getPriority(){
        return ItemBridgeListenerPriority.MEDIUM;
    }

    @Override
    public ItemStack fetchItemStack(String itemName){
        return Food.foods.get(itemName).getItemStack();
    }

    @Override
    public String getItemName(ItemStack stack){
        Food food = PlayerListener.GetFoodFromItemStack(stack);
        if (food == null) return null;

        return food.uniqueID;
    }

    @Override
    public boolean isItem(ItemStack stack, String name){
        return PlayerListener.GetFoodFromItemStack(stack).uniqueID.equals(name);
    }
}

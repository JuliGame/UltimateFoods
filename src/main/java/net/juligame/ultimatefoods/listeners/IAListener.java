package net.juligame.ultimatefoods.listeners;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import net.juligame.ultimatefoods.classes.Food;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IAListener implements Listener {

    @EventHandler
    public void OnIaInit(ItemsAdderLoadDataEvent event){
        for (Food food : Food.foods.values()) {
            food.RegisterRecipe();
        }
    }
}

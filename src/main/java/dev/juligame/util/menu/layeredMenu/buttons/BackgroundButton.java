/*    */ package dev.juligame.util.menu.layeredMenu.buttons;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BackgroundButton
/*    */   extends Button {
/*    */   public ItemStack getButtonItem(Player player) {
/* 11 */     return new ItemStack(Material.AIR);
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/layeredMenu/buttons/BackgroundButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package dev.juligame.util.menu.layeredMenu.buttons;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class ErrorButton
/*    */   extends Button
/*    */ {
/*    */   String name;
/*    */   
/*    */   public ErrorButton(String name) {
/* 16 */     this.name = name;
/*    */   }
/*    */   
/*    */   public ItemStack getButtonItem(Player player) {
/* 20 */     ItemStack item = new ItemStack(Material.BARRIER);
/* 21 */     ItemMeta meta = item.getItemMeta();
/* 22 */     assert meta != null;
/* 23 */     meta.setDisplayName("§f" + this.name);
/* 24 */     String[] desc = { "§7Sorry!", "§7Not implemented yet!" };
/*    */ 
/*    */ 
/*    */     
/* 28 */     meta.setLore(Arrays.asList(desc));
/* 29 */     item.setItemMeta(meta);
/* 30 */     return item;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/layeredMenu/buttons/ErrorButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
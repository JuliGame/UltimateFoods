/*    */ package dev.juligame.util.menu.buttons;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class BackButton
/*    */   extends Button {
/*    */   public BackButton(Menu back) {
/* 15 */     this.back = back;
/*    */   }
/*    */ 
/*    */   
/*    */   private final Menu back;
/*    */   
/*    */   public ItemStack getButtonItem(Player player) {
/* 22 */     ItemStack item = new ItemStack(Material.RED_BED);
/* 23 */     ItemMeta meta = item.getItemMeta();
/*    */     
/* 25 */     if (meta != null) {
/* 26 */       meta.setDisplayName(ChatColor.RED + "Go back");
/* 27 */       item.setItemMeta(meta);
/*    */     } 
/*    */     
/* 30 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clicked(Player player, int i, ClickType clickType, int hb) {
/* 35 */     Button.playNeutral(player);
/*    */     
/* 37 */     this.back.openMenu(player);
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/buttons/BackButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
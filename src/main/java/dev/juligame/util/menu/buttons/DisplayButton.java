/*    */ package dev.juligame.util.menu.buttons;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class DisplayButton extends Button {
/*    */   private ItemStack item;
/*    */   private boolean cancel;
/*    */   
/* 13 */   public void setItem(ItemStack item) { this.item = item; } public void setCancel(boolean cancel) { this.cancel = cancel; } public DisplayButton(ItemStack item, boolean cancel) {
/* 14 */     this.item = item; this.cancel = cancel;
/*    */   }
/*    */   
/* 17 */   public ItemStack getItem() { return this.item; } public boolean isCancel() {
/* 18 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public ItemStack getButtonItem(Player player) {
/* 22 */     if (this.item == null) {
/* 23 */       return new ItemStack(Material.AIR);
/*    */     }
/* 25 */     return this.item;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldCancel(Player player, int slot, ClickType clickType) {
/* 31 */     return this.cancel;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/buttons/DisplayButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
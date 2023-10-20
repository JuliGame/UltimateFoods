/*    */ package dev.juligame.util.menu.buttons;
/*    */ import dev.juligame.util.Callback;
/*    */ import dev.juligame.util.menu.Button;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class ConfirmationButton extends Button {
/*    */   public ConfirmationButton(boolean confirm, Callback<Boolean> callback, boolean closeAfterResponse) {
/* 13 */     this.confirm = confirm; this.callback = callback; this.closeAfterResponse = closeAfterResponse;
/*    */   }
/*    */ 
/*    */   
/*    */   private final boolean confirm;
/*    */   private final Callback<Boolean> callback;
/*    */   private final boolean closeAfterResponse;
/*    */   
/*    */   public ItemStack getButtonItem(Player player) {
/* 22 */     ItemStack item = new ItemStack(this.confirm ? Material.GREEN_WOOL : Material.RED_WOOL);
/* 23 */     ItemMeta meta = item.getItemMeta();
/*    */     
/* 25 */     if (meta != null) {
/* 26 */       meta.setDisplayName(this.confirm ? (ChatColor.GREEN + "Confirm") : (ChatColor.RED + "Cancel"));
/* 27 */       item.setItemMeta(meta);
/*    */     } 
/*    */     
/* 30 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clicked(Player player, int i, ClickType clickType, int hb) {
/* 35 */     if (this.confirm) {
/* 36 */       Button.playSuccess(player);
/*    */     } else {
/* 38 */       Button.playFail(player);
/*    */     } 
/*    */     
/* 41 */     if (this.closeAfterResponse) {
/* 42 */       player.closeInventory();
/*    */     }
/*    */     
/* 45 */     this.callback.callback(Boolean.valueOf(this.confirm));
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/buttons/ConfirmationButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
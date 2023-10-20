/*    */ package dev.juligame.util.menu;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public abstract class Button
/*    */ {
/*    */   public static Button placeholder(final Material material, final byte data, String... title) {
/* 14 */     return new Button()
/*    */       {
/*    */         public ItemStack getButtonItem(Player player) {
/* 17 */           ItemStack item = new ItemStack(material, 1, (short)data);
/* 18 */           ItemMeta meta = item.getItemMeta();
/*    */           
/* 20 */           if (meta != null) {
/* 21 */             meta.setDisplayName(StringUtils.join((Object[])title));
/* 22 */             item.setItemMeta(meta);
/*    */           } 
/*    */           
/* 25 */           return item;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public static void playFail(Player player) {
/* 31 */     player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 20.0F, 0.1F);
/*    */   }
/*    */   
/*    */   public static void playSuccess(Player player) {
/* 35 */     player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 15.0F);
/*    */   }
/*    */   
/*    */   public static void playNeutral(Player player) {
/* 39 */     player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 20.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract ItemStack getButtonItem(Player paramPlayer);
/*    */ 
/*    */   
/*    */   public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {}
/*    */   
/*    */   public boolean shouldCancel(Player player, int slot, ClickType clickType) {
/* 49 */     return true;
/*    */   }
/*    */   
/*    */   public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
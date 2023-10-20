/*    */ package dev.juligame.util.menu.backableMenu;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public abstract class BackableMenu extends Menu {
/*    */   public abstract List<Button> getBackableMenuButtons(Player paramPlayer);
/*    */   
/*    */   public abstract Menu getLastMenu();
/*    */   
/*    */   public Map<Integer, Button> getButtons(Player player) {
/* 20 */     List<Button> buttons = getBackableMenuButtons(player);
/* 21 */     Map<Integer, Button> newbuttons = getButtons();
/* 22 */     for (int i = 0; i < buttons.size(); i++) {
/* 23 */       newbuttons.put(Integer.valueOf(i), buttons.get(i));
/*    */     }
/*    */ 
/*    */     
/* 27 */     newbuttons.put(Integer.valueOf(buttons.size() - 9), new Button()
/*    */         {
/*    */           public ItemStack getButtonItem(Player player) {
/* 30 */             ItemStack itemStack = new ItemStack(Material.ARROW);
/* 31 */             ItemMeta itemMeta = itemStack.getItemMeta();
/* 32 */             itemMeta.setDisplayName(ChatColor.GREEN + "Back");
/* 33 */             itemStack.setItemMeta(itemMeta);
/* 34 */             return itemStack;
/*    */           }
/*    */ 
/*    */           
/*    */           public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
/* 39 */             if (BackableMenu.this.getLastMenu() == null) {
/* 40 */               player.closeInventory();
/*    */             } else {
/* 42 */               BackableMenu.this.getLastMenu().openMenu(player);
/*    */             } 
/*    */           }
/*    */         });
/*    */     
/* 47 */     return newbuttons;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/backableMenu/BackableMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
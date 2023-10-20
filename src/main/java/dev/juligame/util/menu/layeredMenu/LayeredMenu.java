/*    */ package dev.juligame.util.menu.layeredMenu;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import dev.juligame.util.menu.pagination.PaginatedMenu;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ public abstract class LayeredMenu
/*    */   extends PaginatedMenu
/*    */ {
/*    */   public abstract List<Button> getLayeredButtons(Player paramPlayer);
/*    */   
/*    */   public abstract int getPageRows();
/*    */   
/*    */   public String getPrePaginatedTitle(Player player) {
/* 25 */     return getLayeredTitle(player);
/*    */   } public abstract String getLayeredTitle(Player paramPlayer);
/*    */   public abstract Menu getLastMenu();
/*    */   public int getMaxItemsPerPage(Player player) {
/* 29 */     return 9 + 9 * getPageRows();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<Integer, Button> getAllPagesButtons(Player player) {
/* 34 */     List<Button> buttons = getLayeredButtons(player);
/* 35 */     int realPageSize = getMaxItemsPerPage(player);
/* 36 */     int pages = buttons.size() / 9 / getPageRows() + 1;
/* 37 */     Map<Integer, Button> newbuttons = new HashMap<>();
/*    */     
/* 39 */     for (int i = 0; i < pages; i++) {
/* 40 */       int pageItems = Math.min(buttons.size(), getPageRows() * 9);
/*    */       int j;
/* 42 */       for (j = 0; j < pageItems; j++) {
/* 43 */         newbuttons.put(Integer.valueOf(j + i * realPageSize), buttons.get(0));
/* 44 */         buttons.remove(0);
/*    */       } 
/* 46 */       newbuttons.put(Integer.valueOf((i + 1) * realPageSize - 9), new Button()
/*    */           {
/*    */             public ItemStack getButtonItem(Player player) {
/* 49 */               ItemStack itemStack = new ItemStack(Material.ARROW);
/* 50 */               ItemMeta itemMeta = itemStack.getItemMeta();
/* 51 */               itemMeta.setDisplayName(ChatColor.GREEN + "Back");
/* 52 */               itemStack.setItemMeta(itemMeta);
/* 53 */               return itemStack;
/*    */             }
/*    */             
/*    */             public void clicked(Player player, int slot, ClickType clickType, int hotbarButton)
/*    */             {
/* 58 */               if (LayeredMenu.this.getLastMenu() == null) {
/* 59 */                 player.closeInventory();
/*    */               } else {
/* 61 */                 LayeredMenu.this.getLastMenu().openMenu(player);
/*    */               }  }
/*    */           });
/* 64 */       for (j = pageItems + 1; j < pageItems + 9; j++) {
/* 65 */         newbuttons.put(Integer.valueOf(j + (i + 1) * realPageSize), new Button()
/*    */             {
/*    */               public ItemStack getButtonItem(Player player) {
/* 68 */                 return new ItemStack(Material.AIR);
/*    */               }
/*    */             });
/*    */       } 
/*    */     } 
/* 73 */     return newbuttons;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/layeredMenu/LayeredMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
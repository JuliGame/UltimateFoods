/*    */ package dev.juligame.util.menu.pagination;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class PaginatedMenu
/*    */   extends Menu {
/*    */   private int page;
/*    */   
/*    */   public PaginatedMenu() {
/* 14 */     this.page = 1;
/*    */     
/* 16 */     setUpdateAfterClick(false);
/*    */   }
/*    */   
/*    */   public String getTitle(Player player) {
/* 20 */     return getPrePaginatedTitle(player);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPage() {
/*    */     return this.page;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void modPage(Player player, int mod) {
/* 30 */     this.page += mod;
/* 31 */     getButtons().clear();
/* 32 */     openMenu(player);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final int getPages(Player player) {
/* 39 */     int buttonAmount = getAllPagesButtons(player).size();
/*    */     
/* 41 */     if (buttonAmount == 0) {
/* 42 */       return 1;
/*    */     }
/*    */     
/* 45 */     return (int)Math.ceil(buttonAmount / getMaxItemsPerPage(player));
/*    */   }
/*    */ 
/*    */   
/*    */   public final Map<Integer, Button> getButtons(Player player) {
/* 50 */     int minIndex = (int)((this.page - 1) * getMaxItemsPerPage(player));
/* 51 */     int maxIndex = (int)(this.page * getMaxItemsPerPage(player));
/*    */     
/* 53 */     HashMap<Integer, Button> buttons = new HashMap<>();
/*    */     
/* 55 */     buttons.put(Integer.valueOf(0), new PageButton(-1, this));
/* 56 */     buttons.put(Integer.valueOf(8), new PageButton(1, this));
/*    */     
/* 58 */     for (Map.Entry<Integer, Button> entry : getAllPagesButtons(player).entrySet()) {
/* 59 */       int ind = ((Integer)entry.getKey()).intValue();
/* 60 */       if (ind >= minIndex && ind < maxIndex) {
/* 61 */         ind -= (int)(getMaxItemsPerPage(player) * (this.page - 1)) - 9;
/* 62 */         buttons.put(Integer.valueOf(ind), entry.getValue());
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     Map<Integer, Button> global = getGlobalButtons(player);
/* 67 */     if (global != null) {
/* 68 */       for (Map.Entry<Integer, Button> gent : global.entrySet()) {
/* 69 */         buttons.put(gent.getKey(), gent.getValue());
/*    */       }
/*    */     }
/*    */     
/* 73 */     return buttons;
/*    */   }
/*    */   
/*    */   public int getMaxItemsPerPage(Player player) {
/* 77 */     return 18;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<Integer, Button> getGlobalButtons(Player player) {
/* 85 */     return null;
/*    */   }
/*    */   
/*    */   public abstract String getPrePaginatedTitle(Player paramPlayer);
/*    */   
/*    */   public abstract Map<Integer, Button> getAllPagesButtons(Player paramPlayer);
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/pagination/PaginatedMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
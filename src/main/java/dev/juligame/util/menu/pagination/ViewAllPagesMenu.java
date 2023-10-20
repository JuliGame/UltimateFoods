/*    */ package dev.juligame.util.menu.pagination;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import dev.juligame.util.menu.buttons.BackButton;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ViewAllPagesMenu
/*    */   extends Menu {
/*    */   public ViewAllPagesMenu(PaginatedMenu menu) {
/* 14 */     if (menu == null) throw new NullPointerException("menu is marked non-null but is null");  this.menu = menu; }
/*    */   PaginatedMenu menu;
/*    */   public PaginatedMenu getMenu() {
/* 17 */     return this.menu;
/*    */   }
/*    */   
/*    */   public String getTitle(Player player) {
/* 21 */     return "Jump to page";
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<Integer, Button> getButtons(Player player) {
/* 26 */     HashMap<Integer, Button> buttons = new HashMap<>();
/*    */     
/* 28 */     buttons.put(Integer.valueOf(0), new BackButton(this.menu));
/*    */     
/* 30 */     int index = 10;
/*    */     
/* 32 */     for (int i = 1; i <= this.menu.getPages(player); i++) {
/* 33 */       buttons.put(Integer.valueOf(index++), new JumpToPageButton(i, this.menu, (this.menu.getPage() == i)));
/* 34 */       if ((index - 8) % 9 == 0) {
/* 35 */         index += 2;
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return buttons;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAutoUpdate() {
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/pagination/ViewAllPagesMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
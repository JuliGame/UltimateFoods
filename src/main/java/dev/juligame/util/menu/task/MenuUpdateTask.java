/*    */ package dev.juligame.util.menu.task;
/*    */ 
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class MenuUpdateTask
/*    */   implements Runnable
/*    */ {
/*    */   public void run() {
/* 12 */     Menu.currentlyOpenedMenus.forEach((key, value) -> {
/*    */           Player player = Bukkit.getPlayer(key);
/*    */           if (player != null && value.isAutoUpdate())
/*    */             value.openMenu(player); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/task/MenuUpdateTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package dev.juligame.util.menu;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.event.inventory.InventoryAction;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class ButtonListener implements Listener {
/*    */   public ButtonListener(JavaPlugin pl) {
/* 18 */     this.plugin = pl;
/*    */   } JavaPlugin plugin;
/*    */   @EventHandler(priority = EventPriority.HIGHEST)
/*    */   public void onButtonPress(InventoryClickEvent event) {
/* 22 */     Player player = (Player)event.getWhoClicked();
/* 23 */     Menu openMenu = Menu.currentlyOpenedMenus.get(player.getName());
/*    */     
/* 25 */     if (openMenu != null) {
/* 26 */       if (event.getSlot() != event.getRawSlot()) {
/* 27 */         if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
/* 28 */           event.setCancelled(true);
/*    */         }
/*    */         
/*    */         return;
/*    */       } 
/* 33 */       if (openMenu.getButtons().containsKey(Integer.valueOf(event.getSlot()))) {
/* 34 */         Button button = openMenu.getButtons().get(Integer.valueOf(event.getSlot()));
/* 35 */         boolean cancel = button.shouldCancel(player, event.getSlot(), event.getClick());
/* 36 */         if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
/* 37 */           event.setCancelled(true);
/* 38 */           if (event.getCurrentItem() != null) {
/* 39 */             player.getInventory().addItem(new ItemStack[] { event.getCurrentItem() });
/*    */           }
/*    */         } else {
/* 42 */           event.setCancelled(cancel);
/*    */         }

/*    */         
/* 45 */         button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());
/* 46 */         if (Menu.currentlyOpenedMenus.containsKey(player.getName())) {
/* 47 */           Menu newMenu = Menu.currentlyOpenedMenus.get(player.getName());
/* 48 */           if (newMenu == openMenu && 
/* 49 */             openMenu.isUpdateAfterClick()) {
/* 50 */             openMenu.setClosedByMenu(true);
/* 51 */             newMenu.openMenu(player);
/*    */           }
/*    */         
/* 54 */         } else if (button.shouldUpdate(player, event.getSlot(), event.getClick())) {
/* 55 */           openMenu.setClosedByMenu(true);
/* 56 */           openMenu.openMenu(player);
/*    */         } 
/* 58 */         if (event.isCancelled()) {
/* 59 */           Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, player::updateInventory, 1L);
/*    */         }
/*    */       }
/* 62 */       else if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
/* 63 */         event.setCancelled(true);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.HIGH)
/*    */   public void onInventoryClose(InventoryCloseEvent event) {
/* 71 */     Player player = (Player)event.getPlayer();
/* 72 */     Menu openMenu = Menu.currentlyOpenedMenus.get(player.getName());
/*    */     
/* 74 */     if (openMenu != null) {
/* 75 */       openMenu.onClose(player);
/* 76 */       Menu.currentlyOpenedMenus.remove(player.getName());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/ButtonListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
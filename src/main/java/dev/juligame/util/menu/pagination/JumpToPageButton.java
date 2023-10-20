/*    */ package dev.juligame.util.menu.pagination;
/*    */ 
/*    */ import dev.juligame.util.menu.Button;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class JumpToPageButton extends Button {
/*    */   public JumpToPageButton(int page, PaginatedMenu menu, boolean current) {
/* 14 */     this.page = page; this.menu = menu; this.current = current;
/*    */   }
/*    */ 
/*    */   
/*    */   private int page;
/*    */   private PaginatedMenu menu;
/*    */   private boolean current;
/*    */   
/*    */   public ItemStack getButtonItem(Player player) {
/* 23 */     ItemStack item = new ItemStack(this.current ? Material.ENCHANTED_BOOK : Material.BOOK, this.page);
/* 24 */     ItemMeta meta = item.getItemMeta();
/*    */     
/* 26 */     if (meta != null) {
/* 27 */       meta.setDisplayName(ChatColor.RED + "Page " + this.page);
/* 28 */       if (this.current) {
/* 29 */         meta.setLore(Arrays.asList(new String[] { "", ChatColor.GREEN + "Current page" }));
/*    */       }
/*    */       
/* 32 */       item.setItemMeta(meta);
/*    */     } 
/* 34 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clicked(Player player, int i, ClickType clickType, int hb) {
/* 39 */     this.menu.modPage(player, this.page - this.menu.getPage());
/* 40 */     Button.playNeutral(player);
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/pagination/JumpToPageButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
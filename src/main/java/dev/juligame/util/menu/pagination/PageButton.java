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
/*    */ public class PageButton extends Button {
/*    */   public PageButton(int mod, PaginatedMenu menu) {
/* 14 */     this.mod = mod; this.menu = menu;
/*    */   }
/*    */ 
/*    */   
/*    */   private final int mod;
/*    */   private final PaginatedMenu menu;
/*    */   
/*    */   public ItemStack getButtonItem(Player player) {
/* 22 */     ItemStack item = new ItemStack(Material.WHITE_CARPET);
/* 23 */     ItemMeta meta = item.getItemMeta();
/*    */     
/* 25 */     if (meta != null) {
/* 26 */       if (hasNext(player)) {
/* 27 */         item.setType(Material.BLUE_CARPET);
/* 28 */         meta.setDisplayName((this.mod > 0) ? (ChatColor.GREEN + "Next page") : (ChatColor.RED + "Previous page"));
/*    */       } else {
/* 30 */         item.setType(Material.GRAY_CARPET);
/* 31 */         meta.setDisplayName(ChatColor.GRAY + ((this.mod > 0) ? "Last page" : "First page"));
/*    */       } 
/*    */       
/* 34 */       meta.setLore(Arrays.asList(new String[] { "", ChatColor.RED + "Right click to", ChatColor.RED + "jump to a page" }));
/* 35 */       item.setItemMeta(meta);
/*    */     } 
/*    */     
/* 38 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clicked(Player player, int i, ClickType clickType, int hb) {
/* 43 */     if (clickType == ClickType.RIGHT) {
/* 44 */       (new ViewAllPagesMenu(this.menu)).openMenu(player);
/* 45 */       playNeutral(player);
/*    */     }
/* 47 */     else if (hasNext(player)) {
/* 48 */       this.menu.modPage(player, this.mod);
/* 49 */       Button.playNeutral(player);
/*    */     } else {
/* 51 */       Button.playFail(player);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean hasNext(Player player) {
/* 57 */     int pg = this.menu.getPage() + this.mod;
/* 58 */     return (pg > 0 && this.menu.getPages(player) >= pg);
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/pagination/PageButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
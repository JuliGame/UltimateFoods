/*    */ package dev.juligame.classes;
/*    */ 
/*    */ import dev.juligame.classes.enums.editableEnum;
/*    */ import dev.juligame.classes.interfaces.editable;
/*    */ import dev.juligame.premade.EditorMenu;
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class CustomClass implements editable {
/*    */   private String name;
/*    */   private List<String> desc;
/*    */   private Material icon;
/*    */   private EditorMenu lastMenu;
/*    */   private Object object;
/*    */   
/*    */   public CustomClass(Object object, EditorMenu lastMenu) {
/* 24 */     this.name = "§f§l" + ((object != null) ? object.getClass().getSimpleName() : "Custom Class");
/* 25 */     this.icon = Material.COMMAND_BLOCK;
/* 26 */     this.desc = new ArrayList<>();
/* 27 */     this.object = object;
/* 28 */     this.lastMenu = lastMenu;
/* 29 */     this.desc.add("§7This is a Custom Object");
/*    */   }
/*    */   
/*    */   public editableEnum getType() {
/* 33 */     return editableEnum.CUSTOM_CLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getDesc() {
/* 43 */     return this.desc;
/*    */   }
/*    */ 
/*    */   
/*    */   public Material getIcon() {
/* 48 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public Button getButton() {
/* 53 */     return new Button()
/*    */       {
/*    */         public ItemStack getButtonItem(Player player) {
/* 56 */           ItemStack item = new ItemStack(CustomClass.this.getIcon());
/* 57 */           ItemMeta meta = item.getItemMeta();
/* 58 */           assert meta != null;
/* 59 */           meta.setDisplayName(CustomClass.this.getName());
/* 60 */           meta.setLore(CustomClass.this.getDesc());
/* 61 */           item.setItemMeta(meta);
/* 62 */           return item;
/*    */         }
/*    */ 
/*    */         
/*    */         public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
/* 67 */           (new EditorMenu(CustomClass.this.object, (Menu)CustomClass.this.lastMenu, CustomClass.this.lastMenu.onOpen, CustomClass.this.lastMenu.onClose)).openMenu(player);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/classes/CustomClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
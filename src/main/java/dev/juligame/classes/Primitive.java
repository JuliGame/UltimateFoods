/*     */ package dev.juligame.classes;
/*     */ 
/*     */ import dev.juligame.JuliMenus;
/*     */ import dev.juligame.classes.editors.signEditor;
/*     */ import dev.juligame.classes.enums.editableEnum;
/*     */ import dev.juligame.classes.enums.primitiveEnum;
/*     */ import dev.juligame.classes.interfaces.editable;
/*     */ import dev.juligame.util.menu.Button;
/*     */ import dev.juligame.util.menu.layeredMenu.LayeredMenu;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class Primitive
/*     */   implements editable
/*     */ {
/*     */   private LayeredMenu menu;
/*     */   private String name;
/*     */   private List<String> desc;
/*     */   private Material icon;
/*     */   
/*     */   public Primitive(Field field, Object object, LayeredMenu menu) {
/*  31 */     this.menu = menu;
/*  32 */     this.name = "§f§l" + field.getName();
/*  33 */     this.type = primitiveEnum.valueOf(field.getType().getSimpleName().toUpperCase());
/*  34 */     this.field = field;
/*  35 */     this.object = object;
/*     */     
/*     */     try {
/*  38 */       field.setAccessible(true);
/*  39 */       this.desc = new ArrayList<>();
/*  40 */       this.value = field.get(object);
/*  41 */       this.desc.add("§fValue: §b" + field.get(object));
/*  42 */       this.desc.add("§fType: §7" + this.type.name());
/*  43 */     } catch (IllegalAccessException e) {
/*  44 */       e.printStackTrace();
/*     */     } 
/*  46 */     this.icon = (this.type != primitiveEnum.BOOLEAN) ? primitiveEnum.getMaterial(this.type) : (((Boolean)this.value).booleanValue() ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE);
/*     */   }
/*     */   private primitiveEnum type; private Field field; private Object value; private Object object;
/*     */   public editableEnum getType() {
/*  50 */     return editableEnum.valueOf(this.type.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  55 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDesc() {
/*  60 */     return this.desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getIcon() {
/*  65 */     return this.icon;
/*     */   }
/*     */ 
/*     */   
/*     */   public Button getButton() {
/*  70 */     return new Button()
/*     */       {
/*     */         public ItemStack getButtonItem(Player player) {
/*  73 */           ItemStack item = new ItemStack(Primitive.this.getIcon());
/*  74 */           ItemMeta meta = item.getItemMeta();
/*  75 */           assert meta != null;
/*  76 */           meta.setDisplayName(Primitive.this.getName());
/*  77 */           meta.setLore(Primitive.this.getDesc());
/*  78 */           item.setItemMeta(meta);
/*  79 */           return item;
/*     */         }
/*     */ 
/*     */         
/*     */         public void clicked(final Player player, int slot, ClickType clickType, int hotbarButton) {
/*  84 */           if (Primitive.this.type == primitiveEnum.BOOLEAN) {
/*     */             try {
/*  86 */               Primitive.this.field.setAccessible(true);
/*  87 */               Primitive.this.field.set(Primitive.this.object, Boolean.valueOf(!Primitive.this.field.getBoolean(Primitive.this.object)));
/*  88 */               Primitive.this.menu.openMenu(player);
/*  89 */             } catch (IllegalAccessException e) {
/*  90 */               throw new RuntimeException(e);
/*     */             } 
/*     */           } else {
/*  93 */             new signEditor(player, Primitive.this.type, Primitive.this.value.toString(), s -> {
/*     */                   System.out.println("CALLBACK WORKING: " + s);
/*     */                   try {
/*     */                     Primitive.this.field.setAccessible(true);
/*     */                     Primitive.this.field.set(Primitive.this.object, Primitive.this.type.format(s));
/*  98 */                   } catch (IllegalAccessException e) {
/*     */                     throw new RuntimeException(e);
/*     */                   } 
/*     */                   (new BukkitRunnable()
/*     */                     {
/*     */                       public void run() {
/* 104 */                         Primitive.this.menu.openMenu(player);
/*     */                       }
/*     */                     }).runTaskLater((Plugin)JuliMenus.plugin, 1L);
/*     */                   return null;
/*     */                 });
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/classes/Primitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
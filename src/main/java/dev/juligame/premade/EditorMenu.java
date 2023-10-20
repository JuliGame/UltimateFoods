/*    */ package dev.juligame.premade;
/*    */ 
/*    */ import dev.juligame.classes.CustomClass;
/*    */ import dev.juligame.classes.Primitive;
/*    */ import dev.juligame.classes.enums.primitiveEnum;
/*    */ import dev.juligame.util.menu.Button;
/*    */ import dev.juligame.util.menu.Menu;
/*    */ import dev.juligame.util.menu.layeredMenu.LayeredMenu;
/*    */ import dev.juligame.util.menu.layeredMenu.buttons.ErrorButton;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.Callable;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class EditorMenu
/*    */   extends LayeredMenu {
/*    */   Object object;
/*    */   Menu lastMenu;
/*    */   public Callable<Void> onOpen;
/*    */   public Callable<Void> onClose;
/*    */   
/*    */   public EditorMenu(Object object, Menu lastMenu, Callable<Void> onOpen, Callable<Void> onClose) {
/* 26 */     this.object = object;
/* 27 */     this.lastMenu = lastMenu;
/* 28 */     this.onOpen = onOpen;
/* 29 */     this.onClose = onClose;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Button> getLayeredButtons(Player player) {
/* 35 */     List<Button> buttons = new ArrayList<>();
/*    */     
/* 37 */     ArrayList<Field> fields = new ArrayList<>();
/* 38 */     fields.addAll(Arrays.asList(this.object.getClass().getDeclaredFields()));
/* 39 */     fields.sort(Comparator.comparing(o -> o.getType().getSimpleName()));
/*    */     
/* 41 */     for (Field field : fields) {
/* 42 */       if (primitiveEnum.contains(field.getType().getSimpleName().toUpperCase())) {
/* 43 */         Primitive primitive = new Primitive(field, this.object, this);
/* 44 */         buttons.add(primitive.getButton()); continue;
/*    */       } 
/*    */       try {
/* 47 */         if (field.isAccessible()) {
/* 48 */           CustomClass nonPrimitive = new CustomClass(field.get(this.object), this);
/* 49 */           buttons.add(nonPrimitive.getButton()); continue;
/*    */         } 
/* 51 */         buttons.add(new ErrorButton(field.getName()));
/*    */       }
/* 53 */       catch (IllegalAccessException e) {
/* 54 */         buttons.add(new ErrorButton(field.getName()));
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return buttons;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPageRows() {
/* 63 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLayeredTitle(Player player) {
/* 68 */     return "Editor " + this.object.getClass().getSimpleName();
/*    */   }
/*    */ 
/*    */   
/*    */   public Menu getLastMenu() {
/* 73 */     return this.lastMenu;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onOpen(Player player) {
/* 78 */     if (this.onOpen != null) {
/*    */       try {
/* 80 */         this.onOpen.call();
/* 81 */       } catch (Exception e) {
/* 82 */         throw new RuntimeException(e);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void onClose(Player player) {
/* 88 */     if (this.onClose != null)
/*    */       try {
/* 90 */         this.onClose.call();
/* 91 */       } catch (Exception e) {
/* 92 */         throw new RuntimeException(e);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/premade/EditorMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package dev.juligame.util.menu;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public abstract class Menu {
/*     */   public void setButtons(Map<Integer, Button> buttons) {
/*  16 */     this.buttons = buttons; } public void setAutoUpdate(boolean autoUpdate) { this.autoUpdate = autoUpdate; } public void setUpdateAfterClick(boolean updateAfterClick) { this.updateAfterClick = updateAfterClick; } public void setClosedByMenu(boolean closedByMenu) { this.closedByMenu = closedByMenu; } public void setPlaceholder(boolean placeholder) { this.placeholder = placeholder; } public void setPlaceholderButton(Button placeholderButton) { this.placeholderButton = placeholderButton; }
/*     */ 
/*     */   
/*  19 */   public static Map<String, Menu> currentlyOpenedMenus = Maps.newHashMap();
/*     */   public Map<Integer, Button> getButtons() {
/*  21 */     return this.buttons;
/*  22 */   } private Map<Integer, Button> buttons = Maps.newHashMap(); private boolean autoUpdate = false; private boolean updateAfterClick = true;
/*     */   
/*  24 */   public boolean isAutoUpdate() { return this.autoUpdate; }
/*  25 */   private boolean closedByMenu = false; public boolean isUpdateAfterClick() { return this.updateAfterClick; } public boolean isClosedByMenu() {
/*  26 */     return this.closedByMenu; } private boolean placeholder = false; public boolean isPlaceholder() {
/*  27 */     return this.placeholder;
/*     */   }
/*  29 */   private Button placeholderButton = Button.placeholder(Material.REDSTONE_BLOCK, (byte)0, new String[] { " " }); public Button getPlaceholderButton() { return this.placeholderButton; }
/*     */   
/*     */   private ItemStack createItemStack(Player player, Button button) {
/*  32 */     ItemStack item = button.getButtonItem(player);
/*     */     
/*  34 */     if (item.getType() != Material.PLAYER_HEAD) {
/*  35 */       ItemMeta meta = item.getItemMeta();
/*     */       
/*  37 */       if (meta != null && meta.hasDisplayName()) {
/*  38 */         meta.setDisplayName(meta.getDisplayName() + "§b§c§d§e");
/*     */       }
/*     */       
/*  41 */       item.setItemMeta(meta);
/*     */     } 
/*     */     
/*  44 */     return item;
/*     */   }
/*     */   
/*     */   public void openMenu(Player player) {
/*  48 */     this.buttons = getButtons(player);
/*     */     
/*  50 */     Menu previousMenu = currentlyOpenedMenus.get(player.getName());
/*  51 */     Inventory inventory = null;
/*  52 */     int size = (getSize() == -1) ? size(this.buttons) : getSize();
/*  53 */     boolean update = false;
/*     */     
/*  55 */     String title = getTitle(player);
/*  56 */     if (title.length() > 32) {
/*  57 */       title = title.substring(0, 32);
/*     */     }
/*     */     
/*  60 */     if (player.getOpenInventory() != null) {
/*  61 */       if (previousMenu == null) {
/*  62 */         player.closeInventory();
/*     */       } else {
/*  64 */         int previousSize = player.getOpenInventory().getTopInventory().getSize();
/*     */         
/*  66 */         if (previousSize == size && player.getOpenInventory().getTitle().equals(title)) {
/*  67 */           inventory = player.getOpenInventory().getTopInventory();
/*  68 */           update = true;
/*     */         } else {
/*  70 */           previousMenu.setClosedByMenu(true);
/*  71 */           player.closeInventory();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  76 */     if (inventory == null) {
/*  77 */       inventory = Bukkit.createInventory((InventoryHolder)player, size, title);
/*     */     }
/*     */     
/*  80 */     inventory.setContents(new ItemStack[inventory.getSize()]);
/*     */     
/*  82 */     currentlyOpenedMenus.put(player.getName(), this);
/*     */     
/*  84 */     for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
/*  85 */       inventory.setItem(((Integer)buttonEntry.getKey()).intValue(), createItemStack(player, buttonEntry.getValue()));
/*     */     }
/*     */     
/*  88 */     if (isPlaceholder()) {
/*  89 */       for (int index = 0; index < size; index++) {
/*  90 */         if (this.buttons.get(Integer.valueOf(index)) == null) {
/*  91 */           this.buttons.put(Integer.valueOf(index), this.placeholderButton);
/*  92 */           inventory.setItem(index, this.placeholderButton.getButtonItem(player));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  97 */     if (update) {
/*  98 */       player.updateInventory();
/*     */     } else {
/* 100 */       player.openInventory(inventory);
/*     */     } 
/*     */     
/* 103 */     onOpen(player);
/* 104 */     setClosedByMenu(false);
/*     */   }
/*     */   
/*     */   public int size(Map<Integer, Button> buttons) {
/* 108 */     int highest = 0;
/*     */     
/* 110 */     for (Iterator<Integer> iterator = buttons.keySet().iterator(); iterator.hasNext(); ) { int buttonValue = ((Integer)iterator.next()).intValue();
/* 111 */       if (buttonValue > highest) {
/* 112 */         highest = buttonValue;
/*     */       } }
/*     */ 
/*     */     
/* 116 */     return (int)(Math.ceil((highest + 1) / 9.0D) * 9.0D);
/*     */   }
/*     */   
/*     */   public int getSlot(int x, int y) {
/* 120 */     return 9 * y + x;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 124 */     return -1;
/*     */   }
/*     */   
/*     */   public void onOpen(Player player) {}
/*     */   
/*     */   public void onClose(Player player) {}
/*     */   
/*     */   public abstract String getTitle(Player paramPlayer);
/*     */   
/*     */   public abstract Map<Integer, Button> getButtons(Player paramPlayer);
/*     */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/menu/Menu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
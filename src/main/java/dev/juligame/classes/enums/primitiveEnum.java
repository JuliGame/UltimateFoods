/*     */ package dev.juligame.classes.enums;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ public enum primitiveEnum {
/*   6 */   STRING,
/*   7 */   INT,
/*   8 */   CHAR,
/*   9 */   BOOLEAN,
/*  10 */   DOUBLE,
/*  11 */   FLOAT,
/*  12 */   LONG,
/*  13 */   SHORT,
/*  14 */   BYTE;
/*     */   
/*     */   public static boolean contains(String name) {
/*     */     try {
/*  18 */       valueOf(name);
/*  19 */     } catch (IllegalArgumentException e) {
/*  20 */       return false;
/*     */     } 
/*  22 */     return true;
/*     */   }
/*     */   public static Material getMaterial(primitiveEnum primitiveEnum1) {
/*  25 */     switch (primitiveEnum1) {
/*     */       case CHAR:
/*  27 */         return Material.PAPER;
/*     */       case STRING:
/*  29 */         return Material.BOOK;
/*     */       case DOUBLE:
/*     */       case FLOAT:
/*  32 */         return Material.GOLD_BLOCK;
/*     */       case INT:
/*     */       case LONG:
/*     */       case SHORT:
/*     */       case BYTE:
/*  37 */         return Material.IRON_BLOCK;
/*     */       case BOOLEAN:
/*  39 */         return Material.LEVER;
/*     */     } 
/*  41 */     return Material.BARRIER;
/*     */   }
/*     */   
/*     */   public Object format(String s) {
/*  45 */     switch (this) {
/*     */       case CHAR:
/*  47 */         return Character.valueOf(s.charAt(0));
/*     */       case STRING:
/*  49 */         return s;
/*     */       case DOUBLE:
/*  51 */         return Double.valueOf(Double.parseDouble(s));
/*     */       case FLOAT:
/*  53 */         return Float.valueOf(Float.parseFloat(s));
/*     */       case INT:
/*  55 */         return Integer.valueOf(Integer.parseInt(s));
/*     */       case LONG:
/*  57 */         return Long.valueOf(Long.parseLong(s));
/*     */       case SHORT:
/*  59 */         return Short.valueOf(Short.parseShort(s));
/*     */       case BYTE:
/*  61 */         return Byte.valueOf(Byte.parseByte(s));
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isValid(String value) {
/*  67 */     if (value == null) return false; 
/*  68 */     if (getType(value) == DOUBLE && this == FLOAT) return true; 
/*  69 */     if (getType(value) == FLOAT && this == DOUBLE) return true; 
/*  70 */     if (getType(value) == INT && this == LONG) return true; 
/*  71 */     if (getType(value) == INT && this == DOUBLE) return true; 
/*  72 */     if (getType(value) == INT && this == FLOAT) return true; 
/*  73 */     return (getType(value) == this);
/*     */   }
/*     */   public static primitiveEnum getType(String s) {
/*     */     try {
/*  77 */       Integer.parseInt(s);
/*  78 */       return INT;
/*  79 */     } catch (Exception exception) {
/*     */       try {
/*  81 */         Double.parseDouble(s);
/*  82 */         return DOUBLE;
/*  83 */       } catch (Exception exception1) {
/*     */         try {
/*  85 */           Long.parseLong(s);
/*  86 */           return LONG;
/*  87 */         } catch (Exception exception2) {
/*     */           try {
/*  89 */             Float.parseFloat(s);
/*  90 */             return FLOAT;
/*  91 */           } catch (Exception exception3) {
/*     */             try {
/*  93 */               Short.parseShort(s);
/*  94 */               return SHORT;
/*  95 */             } catch (Exception exception4) {
/*     */               try {
/*  97 */                 Byte.parseByte(s);
/*  98 */                 return BYTE;
/*  99 */               } catch (Exception exception5) {
/* 100 */                 if (s.length() == 1) return CHAR; 
/* 101 */                 return STRING;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/classes/enums/primitiveEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
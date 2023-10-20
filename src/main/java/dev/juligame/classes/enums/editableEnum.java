/*    */ package dev.juligame.classes.enums;
/*    */ 
/*    */ public enum editableEnum {
/*  4 */   STRING,
/*  5 */   INT,
/*  6 */   CHAR,
/*  7 */   BOOLEAN,
/*  8 */   DOUBLE,
/*  9 */   FLOAT,
/* 10 */   LONG,
/* 11 */   SHORT,
/* 12 */   BYTE,
/* 13 */   CUSTOM_CLASS,
/* 14 */   LIST;
/*    */   
/*    */   public static boolean contains(String name) {
/*    */     try {
/* 18 */       valueOf(name);
/* 19 */     } catch (IllegalArgumentException e) {
/* 20 */       return false;
/*    */     } 
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/classes/enums/editableEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
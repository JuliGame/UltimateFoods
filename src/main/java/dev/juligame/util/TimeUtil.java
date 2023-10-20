/*     */ package dev.juligame.util;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public final class TimeUtil
/*     */ {
/*     */   private TimeUtil() {
/*  11 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*     */   }
/*  13 */   private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoHHMMSS(int secs) {
/*  22 */     return formatIntoMMSS(secs);
/*     */   }
/*     */   
/*     */   public static String formatLongIntoHHMMSS(long secs) {
/*  26 */     int unconvertedSeconds = (int)secs;
/*  27 */     return formatIntoMMSS(unconvertedSeconds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoMMSS(int secs) {
/*  39 */     int seconds = secs % 60;
/*  40 */     secs -= seconds;
/*     */ 
/*     */     
/*  43 */     long minutesCount = (secs / 60);
/*  44 */     long minutes = minutesCount % 60L;
/*  45 */     minutesCount -= minutes;
/*     */     
/*  47 */     long hours = minutesCount / 60L;
/*  48 */     return ((hours > 0L) ? (((hours < 10L) ? "0" : "") + hours + ":") : "") + ((minutes < 10L) ? "0" : "") + minutes + ":" + ((seconds < 10) ? "0" : "") + seconds;
/*     */   }
/*     */   
/*     */   public static String formatLongIntoMMSS(long secs) {
/*  52 */     int unconvertedSeconds = (int)secs;
/*  53 */     return formatIntoMMSS(unconvertedSeconds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoDetailedString(int secs) {
/*  64 */     if (secs == 0) {
/*  65 */       return "0s";
/*     */     }
/*     */     
/*  68 */     int remainder = secs % 86400;
/*     */     
/*  70 */     int days = secs / 86400;
/*  71 */     int hours = remainder / 3600;
/*  72 */     int minutes = remainder / 60 - hours * 60;
/*  73 */     int seconds = remainder % 3600 - minutes * 60;
/*     */     
/*  75 */     String fDays = (days > 0) ? (" " + days + " d") : "";
/*  76 */     String fHours = (hours > 0) ? (" " + hours + " h") : "";
/*  77 */     String fMinutes = (minutes > 0) ? (" " + minutes + " m") : "";
/*  78 */     String fSeconds = (seconds > 0) ? (" " + seconds + " s") : "";
/*     */     
/*  80 */     return (fDays + fHours + fMinutes + fSeconds).trim();
/*     */   }
/*     */   
/*     */   public static String formatIntoMoreDetailedString(int secs) {
/*  84 */     if (secs == 0) {
/*  85 */       return "0 seconds";
/*     */     }
/*     */     
/*  88 */     int remainder = secs % 86400;
/*  89 */     int days = secs / 86400;
/*  90 */     int hours = remainder / 3600;
/*  91 */     int minutes = remainder / 60 - hours * 60;
/*  92 */     int seconds = remainder % 3600 - minutes * 60;
/*  93 */     String fDays = (days > 0) ? (" " + days + " day" + ((days > 1) ? "s" : "")) : "";
/*  94 */     String fHours = (hours > 0) ? (" " + hours + " hour" + ((hours > 1) ? "s" : "")) : "";
/*  95 */     String fMinutes = (minutes > 0) ? (" " + minutes + " minute" + ((minutes > 1) ? "s" : "")) : "";
/*  96 */     String fSeconds = (seconds > 0) ? (" " + seconds + " second" + ((seconds > 1) ? "s" : "")) : "";
/*  97 */     return (fDays + fHours + fMinutes + fSeconds).trim();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoCalendarString(Date date) {
/* 107 */     return dateFormat.format(date);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseTime(String time) {
/* 118 */     if (time.equals("0") || time.equals("")) {
/* 119 */       return 0;
/*     */     }
/*     */     
/* 122 */     String[] lifeMatch = { "w", "d", "h", "m", "s" };
/* 123 */     int[] lifeInterval = { 604800, 86400, 3600, 60, 1 };
/* 124 */     int seconds = 0;
/*     */     
/* 126 */     for (int i = 0; i < lifeMatch.length; i++) {
/*     */       
/* 128 */       Matcher matcher = Pattern.compile("([0-9]*)" + lifeMatch[i]).matcher(time);
/*     */       
/* 130 */       while (matcher.find()) {
/* 131 */         seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 136 */     return seconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSecondsBetween(Date a, Date b) {
/* 148 */     return Math.abs((int)(a.getTime() - b.getTime()) / 1000);
/*     */   }
/*     */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/TimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
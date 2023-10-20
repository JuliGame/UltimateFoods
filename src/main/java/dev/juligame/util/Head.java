/*    */ package dev.juligame.util;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Base64;
/*    */ 
/*    */ public class Head
/*    */ {
/*    */   public static String getHeadValue(String name) {
/*    */     try {
/* 16 */       String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + name);
/* 17 */       Gson g = new Gson();
/* 18 */       JsonObject obj = (JsonObject)g.fromJson(result, JsonObject.class);
/* 19 */       String uid = obj.get("id").toString().replace("\"", "");
/* 20 */       String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
/* 21 */       obj = (JsonObject)g.fromJson(signature, JsonObject.class);
/* 22 */       String value = obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
/* 23 */       String decoded = new String(Base64.getDecoder().decode(value));
/* 24 */       obj = (JsonObject)g.fromJson(decoded, JsonObject.class);
/* 25 */       String skinURL = obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
/* 26 */       byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
/* 27 */       return new String(Base64.getEncoder().encode(skinByte));
/* 28 */     } catch (Exception exception) {
/* 29 */       return null;
/*    */     } 
/*    */   }
/*    */   private static String getURLContent(String urlStr) {
/* 33 */     BufferedReader in = null;
/* 34 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 36 */     try { URL url = new URL(urlStr);
/* 37 */       in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
/*    */       String str;
/* 39 */       while ((str = in.readLine()) != null) {
/* 40 */         sb.append(str);
/*    */       } }
/* 42 */     catch (Exception exception)
/*    */     
/*    */     { try {
/* 45 */         if (in != null) {
/* 46 */           in.close();
/*    */         }
/* 48 */       } catch (IOException iOException) {} } finally { try { if (in != null) in.close();  } catch (IOException iOException) {} }
/*    */     
/* 50 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/Head.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
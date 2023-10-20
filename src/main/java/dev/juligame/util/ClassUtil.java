/*    */ package dev.juligame.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.security.CodeSource;
/*    */ import java.util.Collection;
/*    */ import java.util.Enumeration;
/*    */ import java.util.jar.JarEntry;
/*    */ import java.util.jar.JarFile;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public final class ClassUtil
/*    */ {
/*    */   private ClassUtil() {
/* 17 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Collection<Class<?>> getClassesInPackage(Plugin plugin, String packageName) {
/*    */     JarFile jarFile;
/* 28 */     Collection<Class<?>> classes = Lists.newArrayList();
/*    */     
/* 30 */     CodeSource codeSource = plugin.getClass().getProtectionDomain().getCodeSource();
/* 31 */     URL resource = codeSource.getLocation();
/* 32 */     String relPath = packageName.replace('.', '/');
/* 33 */     String resPath = resource.getPath().replace("%20", " ");
/* 34 */     String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
/*    */ 
/*    */     
/*    */     try {
/* 38 */       jarFile = new JarFile(jarPath);
/* 39 */     } catch (IOException e) {
/* 40 */       throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
/*    */     } 
/*    */     
/* 43 */     Enumeration<JarEntry> entries = jarFile.entries();
/*    */     
/* 45 */     while (entries.hasMoreElements()) {
/* 46 */       JarEntry entry = entries.nextElement();
/* 47 */       String entryName = entry.getName();
/* 48 */       String className = null;
/*    */       
/* 50 */       if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > relPath.length() + "/".length()) {
/* 51 */         className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
/*    */       }
/*    */       
/* 54 */       if (className != null) {
/* 55 */         Class<?> clazz = null;
/*    */         
/*    */         try {
/* 58 */           clazz = Class.forName(className);
/* 59 */         } catch (ClassNotFoundException e) {
/* 60 */           e.printStackTrace();
/*    */         } 
/*    */         
/* 63 */         if (clazz != null) {
/* 64 */           classes.add(clazz);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/*    */     try {
/* 70 */       jarFile.close();
/* 71 */     } catch (IOException e) {
/* 72 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 75 */     return (Collection<Class<?>>)ImmutableSet.copyOf(classes);
/*    */   }
/*    */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/ClassUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
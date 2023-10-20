/*     */ package dev.juligame.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Bukkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Reflection
/*     */ {
/*  79 */   private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
/*  80 */   private static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
/*  81 */   private static String VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
/*     */ 
/*     */   
/*  84 */   private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
/*  95 */     return getField(target, name, fieldType, 0);
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
/*     */   
/*     */   public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
/* 108 */     return getField(getClass(className), name, fieldType, 0);
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
/*     */   public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
/* 120 */     return getField(target, null, fieldType, index);
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
/*     */   
/*     */   public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
/* 133 */     return getField(getClass(className), fieldType, index);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
/* 138 */     for (Field field : target.getDeclaredFields()) {
/* 139 */       if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
/* 140 */         field.setAccessible(true);
/*     */ 
/*     */         
/* 143 */         return new FieldAccessor<T>()
/*     */           {
/*     */             public T get(Object target)
/*     */             {
/*     */               try {
/* 148 */                 return (T)field.get(target);
/* 149 */               } catch (IllegalAccessException e) {
/* 150 */                 throw new RuntimeException("Cannot access reflection.", e);
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public void set(Object target, Object value) {
/*     */               try {
/* 157 */                 field.set(target, value);
/* 158 */               } catch (IllegalAccessException e) {
/* 159 */                 throw new RuntimeException("Cannot access reflection.", e);
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public boolean hasField(Object target) {
/* 166 */               return field.getDeclaringClass().isAssignableFrom(target.getClass());
/*     */             }
/*     */           };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (target.getSuperclass() != null) {
/* 174 */       return getField(target.getSuperclass(), name, fieldType, index);
/*     */     }
/*     */     
/* 177 */     throw new IllegalArgumentException("Cannot find field with type " + fieldType);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
/* 192 */     return getTypedMethod(getClass(className), methodName, null, params);
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
/*     */ 
/*     */   
/*     */   public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
/* 206 */     return getTypedMethod(clazz, methodName, null, params);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
/* 221 */     for (Method method : clazz.getDeclaredMethods()) {
/* 222 */       if ((methodName == null || method.getName().equals(methodName)) && (returnType == null || method
/* 223 */         .getReturnType().equals(returnType)) && 
/* 224 */         Arrays.equals((Object[])method.getParameterTypes(), (Object[])params)) {
/* 225 */         method.setAccessible(true);
/*     */         
/* 227 */         return new MethodInvoker()
/*     */           {
/*     */             public Object invoke(Object target, Object... arguments)
/*     */             {
/*     */               try {
/* 232 */                 return method.invoke(target, arguments);
/* 233 */               } catch (Exception e) {
/* 234 */                 throw new RuntimeException("Cannot invoke method " + method, e);
/*     */               } 
/*     */             }
/*     */           };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 242 */     if (clazz.getSuperclass() != null) {
/* 243 */       return getMethod(clazz.getSuperclass(), methodName, params);
/*     */     }
/*     */     
/* 246 */     throw new IllegalStateException(String.format("Unable to find method %s (%s).", new Object[] { methodName, Arrays.asList(params) }));
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
/*     */ 
/*     */   
/*     */   public static ConstructorInvoker getConstructor(String className, Class<?>... params) {
/* 260 */     return getConstructor(getClass(className), params);
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
/*     */   
/*     */   public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
/* 273 */     for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/* 274 */       if (Arrays.equals((Object[])constructor.getParameterTypes(), (Object[])params)) {
/* 275 */         constructor.setAccessible(true);
/*     */         
/* 277 */         return new ConstructorInvoker()
/*     */           {
/*     */             public Object invoke(Object... arguments)
/*     */             {
/*     */               try {
/* 282 */                 return constructor.newInstance(arguments);
/* 283 */               } catch (Exception e) {
/* 284 */                 throw new RuntimeException("Cannot invoke constructor " + constructor, e);
/*     */               } 
/*     */             }
/*     */           };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 292 */     throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", new Object[] { clazz, Arrays.asList(params) }));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<Object> getUntypedClass(String lookupName) {
/* 307 */     Class<Object> clazz = (Class)getClass(lookupName);
/* 308 */     return clazz;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getClass(String lookupName) {
/* 342 */     return getCanonicalClass(expandVariables(lookupName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getMinecraftClass(String name) {
/* 352 */     return getCanonicalClass(NMS_PREFIX + "." + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getCraftBukkitClass(String name) {
/* 362 */     return getCanonicalClass(OBC_PREFIX + "." + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getCanonicalClass(String canonicalName) {
/*     */     try {
/* 373 */       return Class.forName(canonicalName);
/* 374 */     } catch (ClassNotFoundException e) {
/* 375 */       throw new IllegalArgumentException("Cannot find " + canonicalName, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String expandVariables(String name) {
/* 387 */     StringBuffer output = new StringBuffer();
/* 388 */     Matcher matcher = MATCH_VARIABLE.matcher(name);
/*     */     
/* 390 */     while (matcher.find()) {
/* 391 */       String variable = matcher.group(1);
/* 392 */       String replacement = "";
/*     */ 
/*     */       
/* 395 */       if ("nms".equalsIgnoreCase(variable)) {
/* 396 */         replacement = NMS_PREFIX;
/* 397 */       } else if ("obc".equalsIgnoreCase(variable)) {
/* 398 */         replacement = OBC_PREFIX;
/* 399 */       } else if ("version".equalsIgnoreCase(variable)) {
/* 400 */         replacement = VERSION;
/*     */       } else {
/* 402 */         throw new IllegalArgumentException("Unknown variable: " + variable);
/*     */       } 
/*     */ 
/*     */       
/* 406 */       if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.') {
/* 407 */         replacement = replacement + ".";
/*     */       }
/* 409 */       matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
/*     */     } 
/*     */     
/* 412 */     matcher.appendTail(output);
/* 413 */     return output.toString();
/*     */   }
/*     */   
/*     */   public static interface ConstructorInvoker {
/*     */     Object invoke(Object... param1VarArgs);
/*     */   }
/*     */   
/*     */   public static interface MethodInvoker {
/*     */     Object invoke(Object param1Object, Object... param1VarArgs);
/*     */   }
/*     */   
/*     */   public static interface FieldAccessor<T> {
/*     */     T get(Object param1Object);
/*     */     
/*     */     void set(Object param1Object1, Object param1Object2);
/*     */     
/*     */     boolean hasField(Object param1Object);
/*     */   }
/*     */ }


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
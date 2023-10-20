package dev.juligame.Jconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NotSerialize {}


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/org/example/NotSerialize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
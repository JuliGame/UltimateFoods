package dev.juligame.util;

import java.io.Serializable;

public interface Callback<T> extends Serializable {
  void callback(T paramT);
}


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/util/Callback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
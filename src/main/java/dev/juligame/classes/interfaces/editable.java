package dev.juligame.classes.interfaces;

import dev.juligame.classes.enums.editableEnum;
import dev.juligame.util.menu.Button;
import java.util.List;
import org.bukkit.Material;

public interface editable {
  String getName();
  
  List<String> getDesc();
  
  Material getIcon();
  
  Button getButton();
  
  editableEnum getType();
}


/* Location:              /home/julian/Downloads/UltimatedFood.jar!/dev/juligame/classes/interfaces/editable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
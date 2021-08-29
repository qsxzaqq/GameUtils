package cc.i9mc.gameutils.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class GUIItem {
    private int size;
    private ItemStack itemStack;
    private GUIAction guiAction;
    private NewGUIAction newGUIAction;
}

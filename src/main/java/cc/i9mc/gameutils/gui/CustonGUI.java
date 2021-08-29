package cc.i9mc.gameutils.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustonGUI {
    public List<GUIItem> items;
    @Getter
    private final Player player;
    @Getter
    private final String title;
    @Getter
    private final int size;

    public CustonGUI(Player player, String title, int size) {
        this.player = player;
        this.title = title;
        this.size = size;

        this.items = new ArrayList<>();
    }

    public void setItem(int size, ItemStack itemStack, GUIAction guiAction) {
        items.add(new GUIItem(size, itemStack, guiAction, null));
    }

    public void setItem(int size, ItemStack itemStack, NewGUIAction guiAction) {
        items.add(new GUIItem(size, itemStack, null, guiAction));
    }

    public void open() {
        Inventory inventory = Bukkit.createInventory(null, size, title);

        for (GUIItem guiItem : items) {
            inventory.setItem(guiItem.getSize(), guiItem.getItemStack());
        }

        if (GUIData.getCurrentGui().containsKey(player)) {
            GUIData.getLastGui().put(player, GUIData.getCurrentGui().get(player));
        }
        GUIData.getCurrentGui().put(player, this);

        player.openInventory(inventory);
    }

    public void replace() {
        CustonGUI custonGUI = GUIData.getLastReplaceGui().getOrDefault(player, GUIData.getCurrentGui().getOrDefault(player, null));
        if(custonGUI == null) return;
        GUIData.getLastReplaceGui().put(player, custonGUI);

        for(GUIItem guiItem : custonGUI.items){
            boolean s = true;
            for(GUIItem guiItem1 : items){
                if (guiItem.getSize() == guiItem1.getSize()) {
                    s = false;
                    break;
                }
            }
            if(s) items.add(guiItem);
        }

        open();
    }
}

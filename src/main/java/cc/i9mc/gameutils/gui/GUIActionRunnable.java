package cc.i9mc.gameutils.gui;

import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GUIActionRunnable extends BukkitRunnable {
    @Setter
    private InventoryClickEvent event;

    @Override
    public void run() {

    }
}

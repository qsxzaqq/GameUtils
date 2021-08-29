package cc.i9mc.gameutils.event.bukkit;

import cc.i9mc.gameutils.gui.CustonGUI;
import cc.i9mc.gameutils.gui.GUIAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

@RequiredArgsConstructor
@ToString
public class BukkitGUIActionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final CustonGUI custonGUI;

    @Getter
    private final GUIAction guiAction;

    @Getter
    private final InventoryClickEvent event;

    @Getter
    @Setter
    private boolean cancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
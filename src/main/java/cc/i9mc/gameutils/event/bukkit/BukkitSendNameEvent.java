package cc.i9mc.gameutils.event.bukkit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by JinVan on 2020/7/22.
 */
@AllArgsConstructor
@ToString
public class BukkitSendNameEvent  extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private String name;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
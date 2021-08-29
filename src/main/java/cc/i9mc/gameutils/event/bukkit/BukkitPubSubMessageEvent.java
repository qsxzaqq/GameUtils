package cc.i9mc.gameutils.event.bukkit;


import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
@ToString
public class BukkitPubSubMessageEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final String channel;
    private final String message;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

}
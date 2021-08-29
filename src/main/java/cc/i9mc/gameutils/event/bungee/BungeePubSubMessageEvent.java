package cc.i9mc.gameutils.event.bungee;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.md_5.bungee.api.plugin.Event;

@RequiredArgsConstructor
@ToString
public class BungeePubSubMessageEvent extends Event {
    private final String channel;
    private final String message;

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }
}
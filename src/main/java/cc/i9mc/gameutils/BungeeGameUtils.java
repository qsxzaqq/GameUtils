package cc.i9mc.gameutils;

import cc.i9mc.gameutils.enums.ServerType;
import cc.i9mc.gameutils.listeners.bungee.ServerInfoListener;
import cc.i9mc.gameutils.mysql.ConnectionPoolHandler;
import cc.i9mc.gameutils.redis.PubSubListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeeGameUtils extends Plugin {
    @Getter
    private static BungeeGameUtils instance;

    @Getter
    private PubSubListener pubSubListener;

    @Getter
    private ConnectionPoolHandler connectionPoolHandler;

    @Override
    public void onLoad() {
        ServerType.setServerType(ServerType.BUNGEECORD);
    }

    @Override
    public void onEnable() {
        instance = this;

        pubSubListener = new PubSubListener();
        getProxy().getScheduler().runAsync(this, pubSubListener);

        connectionPoolHandler = new ConnectionPoolHandler();

        new ServerInfoListener(this);
    }

    @Override
    public void onDisable() {
        pubSubListener.poison();
        connectionPoolHandler.closeAll();
    }

    public void asyncCallEvent(Event event) {
        getProxy().getScheduler().runAsync(this, () -> getProxy().getPluginManager().callEvent(event));
    }
}

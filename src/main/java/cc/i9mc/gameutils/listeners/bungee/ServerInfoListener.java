package cc.i9mc.gameutils.listeners.bungee;

import cc.i9mc.gameutils.BungeeGameUtils;
import cc.i9mc.gameutils.event.bungee.BungeePubSubMessageEvent;
import cc.i9mc.gameutils.utils.JedisUtil;
import cc.i9mc.gameutils.utils.LoggerUtil;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.stream.Collectors;

public class ServerInfoListener implements Listener {
    private final BungeeGameUtils main;

    public ServerInfoListener(BungeeGameUtils main) {
        this.main = main;
        main.getProxy().getPluginManager().registerListener(main, this);
    }

    @EventHandler
    public void onMessage(BungeePubSubMessageEvent event) {
        if (!event.getChannel().equals("ServerManage.ServerNameQuery")) {
            return;
        }

        List<ServerInfo> servers = BungeeGameUtils.getInstance().getProxy().getServers().values().stream().filter(s -> event.getMessage().equals(s.getAddress().getAddress().getHostAddress() + ":" + s.getAddress().getPort())).collect(Collectors.toList());

        if (servers.isEmpty()) {
            LoggerUtil.error("ip is null, no name found! " + event.getMessage());
            return;
        }

        try {
            JedisUtil.publish("ServerManage.ServerNameQuery." + event.getMessage(), servers.get(0).getName());
        } catch (Exception ignored) {

        }
    }
}

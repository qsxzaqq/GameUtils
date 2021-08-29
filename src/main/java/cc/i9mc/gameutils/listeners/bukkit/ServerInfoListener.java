package cc.i9mc.gameutils.listeners.bukkit;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.GameUtilsAPI;
import cc.i9mc.gameutils.chat.ChatHandler;
import cc.i9mc.gameutils.event.bukkit.BukkitPubSubMessageEvent;
import cc.i9mc.gameutils.event.bukkit.BukkitSendNameEvent;
import cc.i9mc.gameutils.utils.LoggerUtil;
import cc.i9mc.gameutils.utils.WordUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerInfoListener implements Listener {
    private final BukkitGameUtils main;

    public ServerInfoListener(BukkitGameUtils main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onMessage(BukkitPubSubMessageEvent event) {
        if (!event.getChannel().equals("ServerManage.ServerNameQuery." + main.getLocalIp())) {
            return;
        }

        if (GameUtilsAPI.getServerName() != null) {
            return;
        }

        LoggerUtil.info("获取名称成功: " + event.getMessage());
        GameUtilsAPI.setServerName(event.getMessage());
        GameUtilsAPI.setGameType(WordUtil.getGameType(event.getMessage()));
        GameUtilsAPI.setServerId(WordUtil.getIntFromString(event.getMessage()));
        main.getPubSubListener().removeChannel("ServerManage.ServerNameQuery." + main.getLocalIp());
        main.getPubSubListener().addChannel("ServerManage.LobbySyncChat");
        main.setChatHandler(new ChatHandler(main));
        BukkitSendNameEvent sendNameEvent = new BukkitSendNameEvent(event.getMessage());
        Bukkit.getPluginManager().callEvent(sendNameEvent);
    }
}

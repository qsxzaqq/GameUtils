package cc.i9mc.gameutils.listeners.bukkit;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.GameUtilsAPI;
import cc.i9mc.gameutils.event.bukkit.BukkitPubSubMessageEvent;
import cc.i9mc.gameutils.utils.LoggerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.StringTokenizer;

public class CommandListener implements Listener {
    private final BukkitGameUtils main;

    public CommandListener(BukkitGameUtils main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onMessage(BukkitPubSubMessageEvent event) {
        if (!event.getChannel().equals("ServerManage.RunCommand")) {
            return;
        }

        if (GameUtilsAPI.getServerName() == null) {
            return;
        }

        StringTokenizer in = new StringTokenizer(event.getMessage(), "|^~");
        String start = in.nextToken();
        String command = in.nextToken();

        if (!start.equals("all")) {
            if (!GameUtilsAPI.getServerName().startsWith(start)) {
                return;
            }
        }

        LoggerUtil.info("执行命令: " + command);
        main.getServer().getScheduler().runTask(main, () -> main.getServer().dispatchCommand(main.getServer().getConsoleSender(), command));
    }
}

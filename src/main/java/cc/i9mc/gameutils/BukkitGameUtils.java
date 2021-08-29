package cc.i9mc.gameutils;

import cc.i9mc.gameutils.chat.ChatHandler;
import cc.i9mc.gameutils.commands.bukkit.Commands;
import cc.i9mc.gameutils.enums.ServerType;
import cc.i9mc.gameutils.gui.GUIListener;
import cc.i9mc.gameutils.listeners.bukkit.CommandListener;
import cc.i9mc.gameutils.listeners.bukkit.ServerInfoListener;
import cc.i9mc.gameutils.mysql.ConnectionPoolHandler;
import cc.i9mc.gameutils.redis.bukkit.PubSubListener;
import cc.i9mc.gameutils.utils.BungeeUtil;
import cc.i9mc.gameutils.utils.JedisUtil;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class BukkitGameUtils extends JavaPlugin {
    @Getter
    private static BukkitGameUtils instance;

    @Getter
    private PubSubListener pubSubListener;

    @Getter
    private ConnectionPoolHandler connectionPoolHandler;

    @Getter
    @Setter
    private ChatHandler chatHandler;

    @Getter
    private GameUtilsAPI gameUtilsAPI;

    @Getter
    private Gson gson;

    @Override
    public void onLoad() {
        ServerType.setServerType(ServerType.BUKKIT);
    }

    @Override
    public void onEnable() {
        instance = this;

        gson = new Gson();
        pubSubListener = new PubSubListener();
        getServer().getScheduler().runTaskAsynchronously(this, pubSubListener);

        connectionPoolHandler = new ConnectionPoolHandler();
        gameUtilsAPI = new GameUtilsAPI();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (GameUtilsAPI.getServerName() != null) {
                    cancel();
                    return;
                }

                JedisUtil.publish("ServerManage.ServerNameQuery", getLocalIp());
            }
        }.runTaskTimerAsynchronously(this, 0, 20 * 5);

        new BungeeUtil(this);
        new ServerInfoListener(this);
        new CommandListener(this);
        new Commands();
        new GUIListener(this);
    }

    @Override
    public void onDisable() {
        pubSubListener.poison();
        connectionPoolHandler.closeAll();
    }

    public String getLocalIp() {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                   // if (i.getHostAddress().startsWith("172.0.0.")) {
                        return i.getHostAddress() + ":" + getServer().getPort();
                  //  }
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public void callEvent(Event event) {
        getServer().getPluginManager().callEvent(event);
    }

    @Deprecated
    public void asyncCallEvent(Event event) {
        getServer().getScheduler().runTaskAsynchronously(this, () -> getServer().getPluginManager().callEvent(event));
    }
}

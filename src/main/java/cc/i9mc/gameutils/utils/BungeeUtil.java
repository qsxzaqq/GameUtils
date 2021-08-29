package cc.i9mc.gameutils.utils;

import cc.i9mc.gameutils.BukkitGameUtils;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BungeeUtil {
    public BungeeUtil(BukkitGameUtils main){
        main.getServer().getMessenger().registerOutgoingPluginChannel(main, "BungeeCord");
    }

    public static void send(String server, Player player) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(BukkitGameUtils.getInstance(), "BungeeCord", bytes.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

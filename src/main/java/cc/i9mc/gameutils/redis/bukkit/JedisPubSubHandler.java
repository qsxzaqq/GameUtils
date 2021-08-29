package cc.i9mc.gameutils.redis.bukkit;

import cc.i9mc.gameutils.BukkitGameUtils;
import redis.clients.jedis.JedisPubSub;

public class JedisPubSubHandler extends JedisPubSub {

    @Override
    public void onMessage(final String s, final String s2) {
        if (s2.trim().length() == 0) {
            return;
        }

        BukkitGameUtils.getInstance().callEvent(new cc.i9mc.gameutils.event.bukkit.BukkitPubSubMessageEvent(s, s2));
    }
}
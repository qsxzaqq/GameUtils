package cc.i9mc.gameutils.redis;

import cc.i9mc.gameutils.BungeeGameUtils;
import redis.clients.jedis.JedisPubSub;

public class JedisPubSubHandler extends JedisPubSub {

    @Override
    public void onMessage(final String s, final String s2) {
        if (s2.trim().length() == 0) {
            return;
        }

        BungeeGameUtils.getInstance().asyncCallEvent(new cc.i9mc.gameutils.event.bungee.BungeePubSubMessageEvent(s, s2));
    }
}
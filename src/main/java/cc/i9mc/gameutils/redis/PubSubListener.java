package cc.i9mc.gameutils.redis;

import cc.i9mc.gameutils.BungeeGameUtils;
import cc.i9mc.gameutils.utils.JedisUtil;
import cc.i9mc.gameutils.utils.LoggerUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PubSubListener implements Runnable {
    private JedisPubSubHandler jedisPubSubHandler;

    private final Set<String> addedChannels = new HashSet<>();

    @Override
    public void run() {
        boolean broken = false;
        try (Jedis rsc = JedisUtil.getJedis()) {
            try {
                jedisPubSubHandler = new JedisPubSubHandler();
                addedChannels.add("ServerManage.ServerNameQuery");

                rsc.subscribe(jedisPubSubHandler, addedChannels.toArray(new String[0]));
            } catch (Exception e) {
                LoggerUtil.info("PubSub error, attempting to recover." + e);
                try {
                    jedisPubSubHandler.unsubscribe();
                } catch (Exception ignored) {
                }
                broken = true;
            }
        } catch (JedisConnectionException e) {
            LoggerUtil.info("PubSub error, attempting to recover in 5 secs.");
            BungeeGameUtils.getInstance().getProxy().getScheduler().schedule(BungeeGameUtils.getInstance(), this, 5, TimeUnit.SECONDS);
        }

        if (broken) {
            run();
        }
    }

    public void addChannel(String... channel) {
        addedChannels.addAll(Arrays.asList(channel));
        jedisPubSubHandler.subscribe(channel);
    }

    public void removeChannel(String... channel) {
        addedChannels.removeAll(Arrays.asList(channel));
        jedisPubSubHandler.unsubscribe(channel);
    }

    public void poison() {
        addedChannels.clear();
        jedisPubSubHandler.unsubscribe();
    }
}

package cc.i9mc.gameutils.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
    private static JedisPool pool = null;

    public static Jedis getJedis() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(2000);
            pool = new JedisPool(config, "yxsj-redis", 6380, 0);
        }

        return pool.getResource();
    }

    public static void publish(String channel, String msg) {
        Jedis jedis = getJedis();
        jedis.publish(channel, msg);
        jedis.close();
    }
}

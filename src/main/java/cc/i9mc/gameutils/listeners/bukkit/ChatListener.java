package cc.i9mc.gameutils.listeners.bukkit;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.GameUtilsAPI;
import cc.i9mc.gameutils.event.bukkit.BukkitPubSubMessageEvent;
import cc.i9mc.gameutils.utils.JedisUtil;
import cc.i9mc.gameutils.utils.LoggerUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class ChatListener implements Listener {
    private final BukkitGameUtils main;

    public ChatListener(BukkitGameUtils main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        String message = PlaceholderAPI.setPlaceholders(event.getPlayer(), GameUtilsAPI.getLobbyChatConfig().replace("%port%", String.valueOf(GameUtilsAPI.getServerId())));

        HashMap<String, String> json = new HashMap<>();
        json.put("group", GameUtilsAPI.getLobbyChatGroup());
        json.put("message", event.getMessage());
        json.put("start", message);
        json.put("player", event.getPlayer().getName());
        json.put("hover", PlaceholderAPI.setPlaceholders(event.getPlayer(), GameUtilsAPI.getLobbyChatHover()));
        String json1 = main.getGson().toJson(json);
        LoggerUtil.info(json1);


        JedisUtil.publish("ServerManage.LobbySyncChat", json1);
    }

    @EventHandler
    public void onMessage(BukkitPubSubMessageEvent event) {
        if (!event.getChannel().equals("ServerManage.LobbySyncChat")) {
            return;
        }

        HashMap json = main.getGson().fromJson(event.getMessage(), HashMap.class);

        if (!GameUtilsAPI.getLobbyChatGroup().equals(json.get("group"))) {
            return;
        }

        TextComponent textComponent = new TextComponent("");
        textComponent.addExtra((String) json.get("start"));
        textComponent.addExtra((String) json.get("message"));
        textComponent.getExtra().get(0).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(((String) json.get("hover")).replace("\\n", "\n"))}));


        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(textComponent));
    }
}

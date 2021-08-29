package cc.i9mc.gameutils.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TitleUtil {

    /**
     * 给一个玩家发送Title信息
     *
     * @param player   发送的玩家
     * @param fadeIn   淡入时间
     * @param stay     停留时间
     * @param fadeOut  淡出时间
     * @param title    主标题
     * @param subTitle 副标题
     */
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        PacketContainer packet;
        if (title != null) {
            String translatedTitle = ChatColor.translateAlternateColorCodes('&', title);
            translatedTitle = translatedTitle.replaceAll("%player%", player.getName());
            packet = pm.createPacket(PacketType.Play.Server.TITLE);
            packet.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE);
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(translatedTitle));
            try {
                pm.sendServerPacket(player, packet, false);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (subTitle != null) {
            String translatedSubTitle = ChatColor.translateAlternateColorCodes('&', subTitle);
            translatedSubTitle = translatedSubTitle.replaceAll("%player%", player.getName());

            packet = pm.createPacket(PacketType.Play.Server.TITLE);
            packet.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(translatedSubTitle));
            try {
                pm.sendServerPacket(player, packet, false);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        packet = pm.createPacket(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
        packet.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);

        try {
            pm.sendServerPacket(player, packet, false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}


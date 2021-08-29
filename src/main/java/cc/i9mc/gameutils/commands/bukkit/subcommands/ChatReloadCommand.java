package cc.i9mc.gameutils.commands.bukkit.subcommands;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.commands.bukkit.SubCommand;
import cc.i9mc.gameutils.utils.LoggerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatReloadCommand extends SubCommand {

    public ChatReloadCommand() {
        super("chatreload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !sender.getName().equals("SuperPi")) {
            return true;
        }

        BukkitGameUtils.getInstance().getChatHandler().reload();
        LoggerUtil.send(sender, "聊天配置重载成功!");

        return false;
    }
}

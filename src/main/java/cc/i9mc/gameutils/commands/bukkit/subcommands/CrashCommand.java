package cc.i9mc.gameutils.commands.bukkit.subcommands;

import cc.i9mc.gameutils.commands.bukkit.SubCommand;
import cc.i9mc.gameutils.utils.CrashUtil;
import cc.i9mc.gameutils.utils.LoggerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class CrashCommand extends SubCommand {
    public CrashCommand() {
        super("crash");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }

        if (args.length == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            CrashUtil.Crasher[] crashers = CrashUtil.Crasher.values();
            for (CrashUtil.Crasher crasher : crashers) {
                stringBuilder.append(crasher.name());
                stringBuilder.append(' ');
            }
            LoggerUtil.send(sender, "&c可用的崩溃模式: " + stringBuilder.toString());
            return true;
        }

        if (args.length != 3) {
            LoggerUtil.send(sender, "§c参数不正确!");
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        if (offlinePlayer == null) {
            LoggerUtil.send(sender, "&c玩家 " + args[1] + " 不存在");
            return true;
        }

        if (!offlinePlayer.isOnline()) {
            LoggerUtil.send(sender, "&c玩家 " + offlinePlayer.getName() + " 不在线");
            return true;
        }

        Player player = offlinePlayer.getPlayer();
        CrashUtil.Crasher crasher = CrashUtil.Crasher.matchCrasher(args[2]);
        if (crasher == null) {
            LoggerUtil.send(sender, "&c错误: 崩溃模式 " + args[2].toUpperCase(Locale.ENGLISH) + " 不存在");

            StringBuilder stringBuilder = new StringBuilder();
            for (CrashUtil.Crasher crasher1 : CrashUtil.Crasher.values()) {
                stringBuilder.append(crasher1.name());
                stringBuilder.append(' ');
            }
            LoggerUtil.send(sender, "&c可用的崩溃模式: " + stringBuilder.toString());
            return true;
        }

        if (crasher.crash(player)) {
            LoggerUtil.send(sender, "§a玩家 " + player.getName() + " 的客户端已崩溃 - 模式 " + crasher.name());
            return true;
        }
        LoggerUtil.send(sender, "&c未能崩溃玩家 " + player.getName() + " 的客户端.");
        return false;
    }
}

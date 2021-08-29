package cc.i9mc.gameutils.commands.bukkit.subcommands;

import cc.i9mc.gameutils.commands.bukkit.SubCommand;
import cc.i9mc.gameutils.utils.JedisUtil;
import cc.i9mc.gameutils.utils.LoggerUtil;
import cc.i9mc.gameutils.utils.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCommand extends SubCommand {

    public CmdCommand() {
        super("cmd");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !sender.getName().equals("SuperPi")) {
            return true;
        }

        if (args.length < 3) {
            LoggerUtil.send(sender, "§c参数不正确!");
            return true;
        }

        JedisUtil.publish("ServerManage.RunCommand", args[1] + "|^~" + StringUtil.arrayToString(args, 2, -1));
        LoggerUtil.send(sender, "命令发送成功");

        return false;
    }
}

package cc.i9mc.gameutils.commands.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.isOp()) {
            return true;
        }

        if (args.length == 0) {
            sendHelp(commandSender);
            return false;
        } else {
            String arg = args[0];
            SubCommand subCommand = Commands.getInstance().getSubCommand(arg);
            if (subCommand == null) {
                subCommand = Commands.getInstance().getSubCommand("*");
            }
            return subCommand.onCommand(commandSender, command, label, args);
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(new String[]{
                "§6§lGameUtils命令帮助:",
                "§a/gu cmd <名称前缀> <命令> §c执行全服命令",
                "§a/gu crash <玩家> <模式> §c崩溃玩家客户端",
                "§a/gu chatreload §c重载聊天配置",
                "§a/gu sound 播放"
        });
    }
}

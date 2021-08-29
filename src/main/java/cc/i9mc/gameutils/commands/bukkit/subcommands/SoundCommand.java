package cc.i9mc.gameutils.commands.bukkit.subcommands;

import cc.i9mc.gameutils.commands.bukkit.SubCommand;
import cc.i9mc.gameutils.utils.LoggerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoundCommand extends SubCommand {

    public SoundCommand() {
        super("sound");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 5) {
            LoggerUtil.send(sender, "§c参数不正确!");
            return true;
        }
        Player player = Bukkit.getPlayerExact(args[1]);
        player.playSound(player.getLocation(), Sound.valueOf(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]));

        LoggerUtil.send(sender, "播放成功");

        return false;
    }
}

package cc.i9mc.gameutils.commands.bukkit;

import cc.i9mc.gameutils.commands.bukkit.subcommands.ChatReloadCommand;
import cc.i9mc.gameutils.commands.bukkit.subcommands.CmdCommand;
import cc.i9mc.gameutils.commands.bukkit.subcommands.CrashCommand;
import cc.i9mc.gameutils.commands.bukkit.subcommands.SoundCommand;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    @Getter
    private static Commands instance = null;

    private final List<SubCommand> commands = new ArrayList<>();

    public Commands() {
        Bukkit.getPluginCommand("gu").setExecutor(new CommandsExecutor());
        commands.add(new CmdCommand());
        commands.add(new SoundCommand());
        commands.add(new CrashCommand());
        commands.add(new ChatReloadCommand());
        instance = this;
    }

    public boolean isSubCommand(String cmd) {
        boolean fact = false;
        for (SubCommand command : commands) {
            if (command.getName().equalsIgnoreCase(cmd)) {
                fact = true;
                break;
            }
        }
        return fact;
    }

    public SubCommand getSubCommand(String cmd) {
        SubCommand fact = null;
        for (SubCommand command : commands) {
            if (command.getName().equalsIgnoreCase(cmd)) {
                fact = command;
                break;
            }
        }
        return fact;
    }
}

package cc.i9mc.gameutils.commands.bukkit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Data
@AllArgsConstructor
public abstract class SubCommand {
    private String name;

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);
}

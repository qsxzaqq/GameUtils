package cc.i9mc.gameutils.gui;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GUIData {

    @Getter
    private static final HashMap<Player, CustonGUI> currentGui = new HashMap<>();

    @Getter
    private static final HashMap<Player, CustonGUI> lastGui = new HashMap<>();

    @Getter
    private static final HashMap<Player, CustonGUI> lastReplaceGui = new HashMap<>();
}

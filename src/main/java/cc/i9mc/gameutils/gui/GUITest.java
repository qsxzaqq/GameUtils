package cc.i9mc.gameutils.gui;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUITest extends CustonGUI {

    public GUITest(Player player) {
        super(player, "123", 1);

        setItem(1, new ItemBuilderUtil().setType(Material.WOOL).getItem(), new GUIAction(1, new GUIActionRunnable() {
            //111
        }, false));
    }
}

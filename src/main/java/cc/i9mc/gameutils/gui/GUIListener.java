package cc.i9mc.gameutils.gui;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.event.bukkit.BukkitGUIActionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.stream.Collectors;

public class GUIListener implements Listener {
    private final BukkitGameUtils main;

    public GUIListener(BukkitGameUtils main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (GUIData.getCurrentGui().containsKey(player)) {
            CustonGUI custonGUI = GUIData.getCurrentGui().get(player);

            if (event.getView().getTopInventory().getTitle().equals(custonGUI.getTitle())) {
                event.setCancelled(true);

                List<GUIItem> guiItems = custonGUI.items.stream().filter(guiItem -> guiItem.getSize() == event.getSlot()).collect(Collectors.toList());
                if (guiItems.isEmpty()) {
                    return;
                }

                GUIAction guiAction = guiItems.get(0).getGuiAction();
                NewGUIAction newGUIAction = guiItems.get(0).getNewGUIAction();

                BukkitGUIActionEvent bukkitGUIActionEvent = new BukkitGUIActionEvent(custonGUI, guiAction, event);
                main.callEvent(bukkitGUIActionEvent);

                if (bukkitGUIActionEvent.isCancelled()) {
                    return;
                }

                if ((guiAction == null ? newGUIAction.getDelay() : guiAction.getDelay()) > 0) {
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        if(guiAction == null){
                            newGUIAction.getRunnable().run(event);
                        }else {
                            if (guiAction.getRunnable() instanceof GUIActionRunnable) {
                                GUIActionRunnable guiActionRunnable = (GUIActionRunnable) guiAction.getRunnable();
                                guiActionRunnable.setEvent(event);
                                guiActionRunnable.run();
                            } else {
                                guiAction.getRunnable().run();
                            }
                        }

                        if (guiAction == null ? newGUIAction.isClose() : guiAction.isClose()) {
                            player.closeInventory();
                        }
                    }, guiAction == null ? newGUIAction.getDelay() : guiAction.getDelay());
                    return;
                }

                if(guiAction == null){
                    newGUIAction.getRunnable().run(event);
                }else {
                    guiAction.getRunnable().run();
                }

                if (guiAction == null ? newGUIAction.isClose() : guiAction.isClose()) {
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (GUIData.getCurrentGui().containsKey(player)) {
            String title = event.getView().getTopInventory().getTitle();
            CustonGUI custonGUI = GUIData.getCurrentGui().get(player);

            if (title.equals(custonGUI.getTitle())) {
                CustonGUI lastGui = GUIData.getLastGui().getOrDefault(player, null);
                GUIData.getLastGui().remove(player);

                if (lastGui != null && title.equals(lastGui.getTitle())) {
                    return;
                }

                GUIData.getCurrentGui().remove(player);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GUIData.getLastReplaceGui().remove(event.getPlayer());
    }
}

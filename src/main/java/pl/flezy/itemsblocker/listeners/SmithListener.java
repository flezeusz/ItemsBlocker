package pl.flezy.itemsblocker.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.inventory.ItemStack;
import pl.flezy.itemsblocker.manager.SmithingManager;

public class SmithListener implements Listener {
    @EventHandler
    public void onSmith(SmithItemEvent event) {
        if (SmithingManager.isNetheriteSmithingBlocked()) {
            ItemStack item = event.getInventory().getResult();
            if (item != null && SmithingManager.isNetheriteItem(item)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @EventHandler
    public void onPrepareSmith(PrepareSmithingEvent event) {
        if (SmithingManager.isNetheriteSmithingBlocked()) {
            ItemStack item = event.getInventory().getResult();
            if (item != null && SmithingManager.isNetheriteItem(item)) {
                event.setResult(null);
            }
        }
    }
}

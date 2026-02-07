package pl.flezy.itemsblocker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.inventory.ItemStack;
import pl.flezy.itemsblocker.manager.BlockManager;

public class CrafterListener implements Listener {

    @EventHandler
    public void onCraterCraft(CrafterCraftEvent event){
        ItemStack item = event.getResult();
        if (BlockManager.isBlocked(item)) {
            event.setCancelled(true);
        }
    }

}

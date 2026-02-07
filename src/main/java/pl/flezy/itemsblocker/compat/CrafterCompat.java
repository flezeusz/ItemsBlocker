package pl.flezy.itemsblocker.compat;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import pl.flezy.itemsblocker.ItemsBlocker;

public class CrafterCompat {

    public static void registerIfPresent(ItemsBlocker plugin) {
        if (!hasCrafterEvent()) {
            return;
        }

        try {
            Class<?> listenerClass = Class.forName(
                    "pl.flezy.itemsblocker.listeners.CrafterListener"
            );

            Listener listener = (Listener) listenerClass
                    .getDeclaredConstructor()
                    .newInstance();

            Bukkit.getPluginManager().registerEvents(
                    listener,
                    plugin
            );

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(
                    "Failed to load CrafterListener", e
            );
        }
    }

    private static boolean hasCrafterEvent() {
        try {
            Class.forName("org.bukkit.event.block.CrafterCraftEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

package pl.flezy.itemsblocker.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class MMUtils {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static String mm(String message) {
        return miniMessage.deserialize(message).toString();
    }
}

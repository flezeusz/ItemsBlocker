package pl.flezy.itemsblocker.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;
import pl.flezy.itemsblocker.ItemsBlocker;

@CommandAlias("itemsblocker|blocker")
@CommandPermission("itemsblocker.command")
public class BlockCommand extends BaseCommand {

    private final ItemsBlocker plugin = ItemsBlocker.getInstance();
    private final MiniMessage mm = MiniMessage.miniMessage();

    @HelpCommand
    public void doHelp(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("item add")
    @Description("Adds an item to the list of blocked items")

    public void onItemAdd(CommandSender sender, Material material) {
        if (plugin.getData().blockedMaterials.contains(material)) {
            sender.sendMessage(mm.deserialize("<red>This item is already blocked"));
            return;
        }
        plugin.getData().blockedMaterials.add(material);
        plugin.getData().save();
        sender.sendMessage(mm.deserialize("<yellow>Item " + material.toString() + " has been blocked"));
    }

    @Subcommand("item remove")
    @Description("Removes an item from the list of blocked items")
    @CommandCompletion("@itemsRemove")
    public void onItemRemove(CommandSender sender, Material material) {
        if (!plugin.getData().blockedMaterials.contains(material)) {
            sender.sendMessage(mm.deserialize("<red>This item is not blocked"));
            return;
        }
        plugin.getData().blockedMaterials.remove(material);
        plugin.getData().save();
        sender.sendMessage(mm.deserialize("<yellow>Item " + material.toString() + " is no longer blocked"));
    }

    @Subcommand("item list")
    @Description("Displays a list of all blocked items")
    public void onItemList(CommandSender sender) {
        if (plugin.getData().blockedMaterials.isEmpty()) {
            sender.sendMessage(mm.deserialize("<red>No blocked items"));
            return;
        }
        sender.sendMessage(mm.deserialize("<yellow>Blocked items:"));
        plugin.getData().blockedMaterials.forEach(material ->
                sender.sendMessage(mm.deserialize("<dark_gray>-<gray> " + material.name()))
        );
    }

    @Subcommand("enchantment add")
    @Description("Adds an enchantment to the list of blocked enchantments, optionally with a level")
    @CommandCompletion("@enchantments [level]")
    public void onEnchantmentAdd(CommandSender sender, String enchantName, @Optional Integer level) {
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage(mm.deserialize("<red>This enchantment does not exist"));
            return;
        }

        if (level == null) level = 1;
        plugin.getData().blockedEnchantments.put(enchant, level);
        plugin.getData().save();
        sender.sendMessage(mm.deserialize("<yellow>Enchantment " + enchant.getKey().getKey() + " has been blocked from level " + level));
    }

    @Subcommand("enchantment remove")
    @Description("Removes enchantments from the list of blocked enchantments")
    @CommandCompletion("@enchantmentsRemove")
    public void onEnchantmentRemove(CommandSender sender, String enchantName) {
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage(mm.deserialize("<red>This enchantment does not exist"));
            return;
        }

        if (!plugin.getData().blockedEnchantments.containsKey(enchant)) {
            sender.sendMessage(mm.deserialize("<red>This enchantment is not blocked"));
            return;
        }

        plugin.getData().blockedEnchantments.remove(enchant);
        plugin.getData().save();
        sender.sendMessage(mm.deserialize("<yellow>Enchantment " + enchant.getKey().getKey() + " is no longer blocked"));
    }

    @Subcommand("enchantment list")
    @Description("Shows all blocked enchantments with levels")
    public void onEnchantmentList(CommandSender sender) {
        if (plugin.getData().blockedEnchantments.isEmpty()) {
            sender.sendMessage(mm.deserialize("<red>No blocked enchantments"));
            return;
        }
        sender.sendMessage(mm.deserialize("<yellow>Blocked enchantments:"));
        plugin.getData().blockedEnchantments
                .forEach((key, value) -> {
                    String enchantmentName = key.getKey().getKey();
                    int level = value;
                    sender.sendMessage(mm.deserialize("<dark_gray>-<gray> " + enchantmentName + " " + level));
                });
    }

    @Subcommand("potion add")
    @Description("Adds a potion effect to the list of blocked effects")
    @CommandCompletion("@potionEffects [amplifier]")
    public void onPotionAdd(CommandSender sender, String potionName, @Optional Integer amplifier) {
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage(mm.deserialize("<red>This potion effect does not exist"));
            return;
        }

        if (amplifier == null) amplifier = 0;
        plugin.getData().blockedPotions.put(potion, amplifier - 1);
        plugin.getData().save();
        sender.sendMessage(mm.deserialize("<yellow>Potion effect " + potion.getKey().getKey() + " has been blocked from amplifier " + amplifier));
    }

    @Subcommand("potion remove")
    @Description("Removes a potion effect from the list of blocked effects")
    @CommandCompletion("@potionEffectsRemove")
    public void onPotionRemove(CommandSender sender, String potionName) {
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage(mm.deserialize("<red>This potion effect does not exist"));
            return;
        }

        if (!plugin.getData().blockedPotions.containsKey(potion)) {
            sender.sendMessage(mm.deserialize("<red>This potion effect is not blocked"));
            return;
        }

        plugin.getData().blockedPotions.remove(potion);
        plugin.getData().save();
        sender.sendMessage(mm.deserialize("<yellow>Potion effect " + potion.getKey().getKey() + " is no longer blocked"));
    }

    @Subcommand("potion list")
    @Description("Shows all blocked potion effects")
    public void onPotionList(CommandSender sender) {
        if (plugin.getData().blockedPotions.isEmpty()) {
            sender.sendMessage(mm.deserialize("<red>No blocked potion effects"));
            return;
        }
        sender.sendMessage(mm.deserialize("<yellow>Blocked potions effects:"));

        plugin.getData().blockedPotions
                .forEach((key, value) -> {
                    String potionName = key.getKey().getKey();
                    int amplifier = value + 1;
                    sender.sendMessage(mm.deserialize("<dark_gray>-<gray> " + potionName + " " + amplifier));
                });
    }

    @Subcommand("netherite")
    @Description("Blocks or allows smithing of netherite items")
    @CommandCompletion("block|allow")
    public void netherite(CommandSender sender, @Values("block|allow") String action) {
        if (action.equalsIgnoreCase("block")) {
            if (plugin.getData().blockNetherite) {
                sender.sendMessage(mm.deserialize("<red>Smithing netherite items is already blocked"));
                return;
            }
            plugin.getData().blockNetherite = true;
            plugin.getData().save();
            sender.sendMessage(mm.deserialize("<yellow>Smithing netherite items is now blocked"));
        }
        else if (action.equalsIgnoreCase("allow")) {
            if (!plugin.getData().blockNetherite) {
                sender.sendMessage(mm.deserialize("<red>Smithing netherite items is already allowed"));
                return;
            }
            plugin.getData().blockNetherite = false;
            plugin.getData().save();
            sender.sendMessage(mm.deserialize("<yellow>Smithing netherite items is now allowed"));
        }
    }
}

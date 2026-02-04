package pl.flezy.itemsblocker.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
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

    @HelpCommand
    public void doHelp(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("item add")
    @Description("Adds an item to the list of blocked items")

    public void onItemAdd(CommandSender sender, Material material) {
        if (plugin.getData().blockedMaterials.contains(material)) {
            sender.sendMessage("§cThis item is already blocked");
            return;
        }
        plugin.getData().blockedMaterials.add(material);
        plugin.getData().save();
        sender.sendMessage("§eItem " + material.toString() + " has been blocked");
    }

    @Subcommand("item remove")
    @Description("Removes an item from the list of blocked items")
    @CommandCompletion("@itemsRemove")
    public void onItemRemove(CommandSender sender, Material material) {
        if (!plugin.getData().blockedMaterials.contains(material)) {
            sender.sendMessage("§cThis item is not blocked");
            return;
        }
        plugin.getData().blockedMaterials.remove(material);
        plugin.getData().save();
        sender.sendMessage("§eItem " + material.toString() + " is no longer blocked");
    }

    @Subcommand("item list")
    @Description("Displays a list of all blocked items")
    public void onItemList(CommandSender sender) {
        if (plugin.getData().blockedMaterials.isEmpty()) {
            sender.sendMessage("§cNo blocked items");
            return;
        }
        sender.sendMessage("§eBlocked items:");
        plugin.getData().blockedMaterials.forEach(mat ->
                sender.sendMessage("§8-§7 " + mat.name())
        );
    }

    @Subcommand("enchantment add")
    @Description("Adds an enchantment to the list of blocked enchantments, optionally with a level")
    @CommandCompletion("@enchantments [level]")
    public void onEnchantmentAdd(CommandSender sender, String enchantName, @Optional Integer level) {
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cThis enchantment does not exist");
            return;
        }

        if (level == null) level = 1;
        plugin.getData().blockedEnchants.put(enchant, level);
        plugin.getData().save();
        sender.sendMessage("§eEnchantment " + enchant.getKey() + " has been blocked from level " + level);
    }

    @Subcommand("enchantment remove")
    @Description("Removes enchantments from the list of blocked enchantments")
    @CommandCompletion("@enchantmentsRemove")
    public void onEnchantmentRemove(CommandSender sender, String enchantName) {
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cThis enchantment does not exist");
            return;
        }

        if (!plugin.getData().blockedEnchants.containsKey(enchant)) {
            sender.sendMessage("§cThis enchantment is not blocked");
            return;
        }

        plugin.getData().blockedEnchants.remove(enchant);
        plugin.getData().save();
        sender.sendMessage("§eEnchantment " + enchant.getKey() + " is no longer blocked");
    }

    @Subcommand("enchantment list")
    @Description("Shows all blocked enchantments with levels")
    public void onEnchantmentList(CommandSender sender) {
        if (plugin.getData().blockedEnchants.isEmpty()) {
            sender.sendMessage("§cNo blocked enchantments");
            return;
        }
        sender.sendMessage("§eBlocked enchantments:");
        plugin.getData().blockedEnchants
                .forEach((key, value) -> {
                    String enchantName = key.getKey().getKey();
                    int level = value;
                    sender.sendMessage("§8-§7 " + enchantName + "  " + level);
                });
    }

    @Subcommand("potion add")
    @Description("Adds a potion effect to the list of blocked effects")
    @CommandCompletion("@potionEffects [level]")
    public void onPotionAdd(CommandSender sender, String potionName, @Optional Integer level) {
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cThis potion effect does not exist");
            return;
        }

        if (level == null) level = 1;
        plugin.getData().blockedPotions.put(potion, level);
        plugin.getData().save();
        sender.sendMessage("§ePotion effect " + potion.getKey() + " has been blocked from level " + level);
    }

    @Subcommand("potion remove")
    @Description("Removes a potion effect from the list of blocked effects")
    @CommandCompletion("@potionEffectsRemove")
    public void onPotionRemove(CommandSender sender, String potionName) {
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cThis potion effect does not exist");
            return;
        }

        if (!plugin.getData().blockedPotions.containsKey(potion)) {
            sender.sendMessage("§cThis potion effect is not blocked");
            return;
        }

        plugin.getData().blockedPotions.remove(potion);
        plugin.getData().save();
        sender.sendMessage("§ePotion effect " + potion.getKey() + " is no longer blocked");
    }

    @Subcommand("potion list")
    @Description("Shows all blocked potion effects")
    public void onPotionList(CommandSender sender) {
        if (plugin.getData().blockedPotions.isEmpty()) {
            sender.sendMessage("§cNo blocked potion effects");
            return;
        }
        sender.sendMessage("§eBlocked potions effects:");

        plugin.getData().blockedPotions
                .forEach((key, value) -> {
                    String potionName = key.getKey().getKey();
                    int level = value;
                    sender.sendMessage("§8-§7 " + potionName + "  " + level);
                });
    }

    @Subcommand("netherite")
    @Description("Blocks or allows smithing of netherite items")
    @CommandCompletion("block|allow")
    public void netherite(CommandSender sender, @Values("block|allow") String action) {
        if (action.equalsIgnoreCase("block")) {
            plugin.getData().blockNetherite = true;
            plugin.getData().save();
            sender.sendMessage("§eSmithing netherite items is now blocked");
        }
        else if (action.equalsIgnoreCase("allow")) {
            plugin.getData().blockNetherite = true;
            plugin.getData().save();
            sender.sendMessage("§eSmithing netherite items is now allowed");
        }
    }
}

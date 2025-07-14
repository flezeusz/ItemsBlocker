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

    private final ItemsBlocker plugin = ItemsBlocker.instance();

    @HelpCommand
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("item add")
    @Description("Adds an item to the list of blocked items")

    public void addItem(CommandSender sender, Material material) {
        if (plugin.dataConfiguration().blockedMaterials.contains(material)) {
            sender.sendMessage("§cThis item is already blocked");
            return;
        }
        plugin.dataConfiguration().blockedMaterials.add(material);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eItem " + material.toString() + " has been blocked");
    }

    @Subcommand("item remove")
    @Description("Removes an item from the list of blocked items")
    @CommandCompletion("@itemsRemove")
    public void removeItem(CommandSender sender, Material material) {
        if (!plugin.dataConfiguration().blockedMaterials.contains(material)) {
            sender.sendMessage("§cThis item is not blocked");
            return;
        }
        plugin.dataConfiguration().blockedMaterials.remove(material);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eItem " + material.toString() + " is no longer blocked");
    }

    @Subcommand("item list")
    @Description("Displays a list of all blocked items")
    public void listItems(CommandSender sender) {
        if (plugin.dataConfiguration().blockedMaterials.isEmpty()) {
            sender.sendMessage("§cNo blocked items");
            return;
        }
        sender.sendMessage("§eBlocked items:");
        plugin.dataConfiguration().blockedMaterials.forEach(mat ->
                sender.sendMessage("§8-§7 " + mat.name())
        );
    }

    @Subcommand("enchantment add")
    @Description("Adds an enchantment to the list of blocked enchantments, optionally with a level")
    @CommandCompletion("@enchantments [level]")
    public void addEnchant(CommandSender sender, String enchantName, @Optional Integer level) {
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cThis enchantment does not exist");
            return;
        }

        if (level == null) level = 1;
        plugin.dataConfiguration().blockedEnchants.put(enchant, level);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eEnchantment " + enchant.getKey() + " has been blocked from level " + level);
    }

    @Subcommand("enchantment remove")
    @Description("Removes enchantments from the list of blocked enchantments")
    @CommandCompletion("@enchantmentsRemove")
    public void removeEnchant(CommandSender sender, String enchantName) {
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cThis enchantment does not exist");
            return;
        }

        if (!plugin.dataConfiguration().blockedEnchants.containsKey(enchant)) {
            sender.sendMessage("§cThis enchantment is not blocked");
            return;
        }

        plugin.dataConfiguration().blockedEnchants.remove(enchant);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eEnchantment " + enchant.getKey() + " is no longer blocked");
    }

    @Subcommand("enchantment list")
    @Description("Shows all blocked enchantments with levels")
    public void onListEnchants(CommandSender sender) {
        if (plugin.dataConfiguration().blockedEnchants.isEmpty()) {
            sender.sendMessage("§cNo blocked enchantments");
            return;
        }
        sender.sendMessage("§eBlocked enchantments:");
        plugin.dataConfiguration().blockedEnchants
                .forEach((key, value) -> {
                    String enchantName = key.getKey().getKey();
                    int level = value;
                    sender.sendMessage("§8-§7 " + enchantName + "  " + level);
                });
    }

    @Subcommand("potion add")
    @Description("Adds a potion effect to the list of blocked effects")
    @CommandCompletion("@potionEffects [level]")
    public void addPotion(CommandSender sender, String potionName, @Optional Integer level) {
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cThis potion effect does not exist");
            return;
        }

        if (level == null) level = 1;
        plugin.dataConfiguration().blockedPotions.put(potion, level);
        plugin.dataConfiguration().save();
        sender.sendMessage("§ePotion effect " + potion.getKey() + " has been blocked from level " + level);
    }

    @Subcommand("potion remove")
    @Description("Removes a potion effect from the list of blocked effects")
    @CommandCompletion("@potionEffectsRemove")
    public void removePotion(CommandSender sender, String potionName) {
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cThis potion effect does not exist");
            return;
        }

        if (!plugin.dataConfiguration().blockedPotions.containsKey(potion)) {
            sender.sendMessage("§cThis potion effect is not blocked");
            return;
        }

        plugin.dataConfiguration().blockedPotions.remove(potion);
        plugin.dataConfiguration().save();
        sender.sendMessage("§ePotion effect " + potion.getKey() + " is no longer blocked");
    }

    @Subcommand("potion list")
    @Description("Shows all blocked potion effects")
    public void listPotions(CommandSender sender) {
        if (plugin.dataConfiguration().blockedPotions.isEmpty()) {
            sender.sendMessage("§cNo blocked potion effects");
            return;
        }
        sender.sendMessage("§eBlocked potions effects:");

        plugin.dataConfiguration().blockedPotions
                .forEach((key, value) -> {
                    String potionName = key.getKey().getKey();
                    int level = value;
                    sender.sendMessage("§8-§7 " + potionName + "  " + level);
                });
    }

    @Subcommand("netherite")
    @Description("Blocks smithing of netherite items")
    @CommandCompletion("true|false")
    public void netherite(CommandSender sender, boolean bool) {
        plugin.dataConfiguration().netherite = bool;
        plugin.dataConfiguration().save();
        sender.sendMessage("§eBlocking of netherite item smithing has been set to " + bool);
    }
}

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

@CommandAlias("block|itemsblocker")
@CommandPermission("op")
public class BlockCommand extends BaseCommand {

    private final ItemsBlocker plugin = ItemsBlocker.instance();

    @HelpCommand
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("item add")
    public void addItem(CommandSender sender, Material material){
        if (plugin.dataConfiguration().blockedMaterials.contains(material)){
            sender.sendMessage("§cTen item jest już zablokowany");
            return;
        }
        plugin.dataConfiguration().blockedMaterials.add(material);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eItem "+material.toString()+" został zablokowany");
    }

    @Subcommand("item remove")
    @CommandCompletion("@itemsRemove")
    public void removeItem(CommandSender sender, Material material){
        if (!plugin.dataConfiguration().blockedMaterials.contains(material)){
            sender.sendMessage("§cTen item nie jest zablokowany");
            return;
        }
        plugin.dataConfiguration().blockedMaterials.add(material);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eItem "+material.toString()+" został odblokowany");
    }

    @Subcommand("item list")
    public void listItems(CommandSender sender) {
        if (plugin.dataConfiguration().blockedMaterials.isEmpty()) {
            sender.sendMessage("§7Brak zablokowanych itemów.");
            return;
        }
        sender.sendMessage("§eZablokowane itemy:");
        plugin.dataConfiguration().blockedMaterials.forEach(mat ->
                sender.sendMessage("§8-§7 " + mat.name())
        );
    }

    @Subcommand("enchantment add")
    @CommandCompletion("@enchantments [poziom]")
    public void addEnchant(CommandSender sender,  String enchantName, @Optional Integer level){
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cTen enchantment nie istnieje");
            return;
        }

        if (level==null) level=1;
        plugin.dataConfiguration().blockedEnchants.put(enchant,level);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eEnchantment "+enchant.getKey()+" został zablokowany od poziomu "+level);
    }

    @Subcommand("enchantment remove")
    @CommandCompletion("@enchantmentsRemove")
    public void removeEnchant(CommandSender sender, String enchantName){
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cTen enchantment nie istnieje");
            return;
        }

        if (!plugin.dataConfiguration().blockedEnchants.containsKey(enchant)){
            sender.sendMessage("§cTen enchant nie jest zablokowany");
            return;
        }

        plugin.dataConfiguration().blockedEnchants.remove(enchant);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eEnchantment "+enchant.getKey()+" został odblokowany");
    }

    @Subcommand("enchantment list")
    public void onListEnchants(CommandSender sender) {
        if (plugin.dataConfiguration().blockedEnchants.isEmpty()) {
            sender.sendMessage("§7Brak zablokowanych enchantów.");
            return;
        }
        sender.sendMessage("§eZablokowane enchanty:");
        plugin.dataConfiguration().blockedEnchants.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .forEach(entry -> {
                    String enchantName = entry.getKey().getKey().getKey();
                    int level = entry.getValue();
                    sender.sendMessage("§8-§7 " + enchantName + "  " + level);
                });
    }

    @Subcommand("potion add")
    @CommandCompletion("@potionEffects [poziom]")
    public void addPotion(CommandSender sender, String potionName, @Optional Integer level){
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cTen efekt nie istnieje");
            return;
        }

        if (level==null) level=1;
        plugin.dataConfiguration().blockedPotions.put(potion,level);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eEfekt "+potion.getKey()+" został zablokowany od poziomu "+level);
    }

    @Subcommand("potion remove")
    @CommandCompletion("@potionEffectsRemove")
    public void removePotion(CommandSender sender, String potionName){
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cTen efekt nie istnieje");
            return;
        }

        if (!plugin.dataConfiguration().blockedPotions.containsKey(potion)){
            sender.sendMessage("§cTen efekt nie jest zablokowany");
            return;
        }

        plugin.dataConfiguration().blockedPotions.remove(potion);
        plugin.dataConfiguration().save();
        sender.sendMessage("§eEfekt "+potion.getKey()+" został odblokowany");
    }

    @Subcommand("potion list")
    public void listPotions(CommandSender sender) {
        if (plugin.dataConfiguration().blockedPotions.isEmpty()) {
            sender.sendMessage("§7Brak zablokowanych efektów potionów.");
            return;
        }
        sender.sendMessage("§eZablokowane potiony:");

        plugin.dataConfiguration().blockedPotions.forEach((key, value) -> {
            String potionName = key.getKey().getKey();
            int level = value;
            sender.sendMessage("§8-§7 " + potionName + "  " + level);
        });
    }

    @Subcommand("netherite")
    @CommandCompletion("true|false")
    public void netherite(CommandSender sender, boolean bool){
        plugin.dataConfiguration().netherite = bool;
        sender.sendMessage("§eCraftowanie netherytowych itemów zostało ustawione na "+bool);
    }
}

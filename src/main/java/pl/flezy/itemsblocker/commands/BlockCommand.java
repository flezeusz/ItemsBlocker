package pl.flezy.itemsblocker.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.PaperCommandCompletions;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.*;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;
import pl.flezy.itemsblocker.ItemsBlocker;

@CommandAlias("block|itemsblocker")
@CommandPermission("op")
public class BlockCommand extends BaseCommand {

    @HelpCommand
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("item add")
    public void addItem(CommandSender sender, Material material){
        if (ItemsBlocker.instance().dataConfiguration().blockedMaterials.contains(material)){
            sender.sendMessage("§cTen item jest już zablokowany");
            return;
        }
        ItemsBlocker.instance().dataConfiguration().blockedMaterials.add(material);
        sender.sendMessage("§eItem "+material.toString()+" został zablokowany");
    }

    @Subcommand("item remove")
    public void removeItem(CommandSender sender, Material material){
        if (!ItemsBlocker.instance().dataConfiguration().blockedMaterials.contains(material)){
            sender.sendMessage("§cTen item nie jest zablokowany");
            return;
        }
        ItemsBlocker.instance().dataConfiguration().blockedMaterials.add(material);
        sender.sendMessage("§eItem "+material.toString()+" został odblokowany");
    }

    @Subcommand("enchantment add")
    @CommandCompletion("@enchantments")
    public void addEnchant(CommandSender sender,  String enchantName, Integer level){
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cTen enchantment nie istnieje");
        }

        ItemsBlocker.instance().dataConfiguration().blockedEnchants.put(enchant,level);
        sender.sendMessage("§eEnchantment "+enchant.getKey()+" został zablokowany od poziomu "+level);
    }

    @Subcommand("enchantment remove")
    @CommandCompletion("@enchantments")
    public void removeEnchant(CommandSender sender, String enchantName){
        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
        if (enchant == null) {
            sender.sendMessage("§cTen enchantment nie istnieje");
        }

        if (ItemsBlocker.instance().dataConfiguration().blockedEnchants.containsKey(enchant)){
            sender.sendMessage("§cTen enchant nie jest zablokowany");
        }

        ItemsBlocker.instance().dataConfiguration().blockedEnchants.remove(enchant);
        sender.sendMessage("§eEnchantment "+enchant.getKey()+" został odblokowany");
    }

    @Subcommand("potion add")
    @CommandCompletion("@potionEffects")
    public void addPotion(CommandSender sender, String potionName, Integer level){
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cTen efekt nie istnieje");
        }

        ItemsBlocker.instance().dataConfiguration().blockedPotions.put(potion,level-1);
        sender.sendMessage("§eEfekt "+potion.getKey()+" został zablokowany od poziomu "+level);
    }

    @Subcommand("potion remove")
    @CommandCompletion("@potionEffects")
    public void removePotion(CommandSender sender, String potionName){
        PotionEffectType potion = PotionEffectType.getByKey(NamespacedKey.minecraft(potionName));
        if (potion == null) {
            sender.sendMessage("§cTen efekt nie istnieje");
        }

        if (ItemsBlocker.instance().dataConfiguration().blockedPotions.containsKey(potion)){
            sender.sendMessage("§cTen efekt nie jest zablokowany");
        }

        ItemsBlocker.instance().dataConfiguration().blockedPotions.remove(potion);
        sender.sendMessage("§eEfekt "+potion.getKey()+" został odblokowany");
    }

    @Subcommand("netherite")
    public void netherite(CommandSender sender, boolean bool){
        ItemsBlocker.instance().dataConfiguration().netherite = bool;
        sender.sendMessage("§eCraftowanie netherytowych itemów zostało ustawione na "+bool);
    }
}

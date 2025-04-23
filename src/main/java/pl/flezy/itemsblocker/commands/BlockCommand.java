package pl.flezy.itemsblocker.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Material;
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
    public void addEnchant(CommandSender sender, Enchantment enchant, Integer level){
        ItemsBlocker.instance().dataConfiguration().blockedEnchants.put(enchant,level);
        sender.sendMessage("§eEnchantment "+enchant.getKey()+" został zablokowany od poziomu "+level);
    }

    @Subcommand("enchantment add")
    public void removeEnchant(CommandSender sender, Enchantment enchant){
        ItemsBlocker.instance().dataConfiguration().blockedEnchants.remove(enchant);
        sender.sendMessage("§eEnchantment "+enchant.getKey()+" został odblokowany");
    }

    @Subcommand("potion add")
    public void addPotion(CommandSender sender, PotionEffectType potion, Integer level){
        ItemsBlocker.instance().dataConfiguration().blockedPotions.put(potion,level-1);
        sender.sendMessage("§eEfekt "+potion.getKey()+" został zablokowany od poziomu "+level);
    }

    @Subcommand("potion remove")
    public void removePotion(CommandSender sender, PotionEffectType potion){
        ItemsBlocker.instance().dataConfiguration().blockedPotions.remove(potion);
        sender.sendMessage("§eEfekt "+potion.getKey()+" został odblokowany");
    }

    @Subcommand("netherite")
    public void netherite(CommandSender sender, boolean bool){
        ItemsBlocker.instance().dataConfiguration().netherite = bool;
        sender.sendMessage("§eCraftowanie netherytowych itemów zostało ustawione na "+bool);
    }
}

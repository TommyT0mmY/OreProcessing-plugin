package com.mccitylife.oreprocessing.commands;

import com.mccitylife.oreprocessing.OreProcessing;
import com.mccitylife.oreprocessing.utility.Permissions;
import com.mccitylife.oreprocessing.utility.XMaterial;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import sun.util.resources.cldr.ext.TimeZoneNames_en_MG;

public class Processhovel implements CommandExecutor {

    private OreProcessing mainClass = OreProcessing.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //sender type check
        if (!(sender instanceof Player)) {
            sender.sendMessage(mainClass.messages.getMessage("only-players-command"));
            return true;
        }

        Player p = (Player) sender;

        //permission check
        if (!p.hasPermission(Permissions.GET_SHOVEL.getNode())) {
            p.sendMessage(mainClass.messages.formattedMessage("§c", "invalid-permissions"));
            return true;
        }

        //syntax check
        if (args.length != 0) {
            p.sendMessage(mainClass.messages.usageMessage("processhovel"));
            return true;
        }

        // SHOVEL ITEM //
        ItemStack shovel = XMaterial.DIAMOND_SHOVEL.parseItem();
        ItemMeta shovelMeta = shovel.getItemMeta();
        NamespacedKey key = new NamespacedKey(mainClass, "oreprocessing");
        PersistentDataContainer data = shovelMeta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, "processhovel");
        shovelMeta.setDisplayName("§a§oOre Processing Shovel");
        shovel.setItemMeta(shovelMeta);

        p.getInventory().addItem(shovel);
        p.sendMessage(mainClass.messages.formattedMessage("§a", "received_shovel"));
        return true;
    }
}

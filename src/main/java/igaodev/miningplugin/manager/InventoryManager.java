package igaodev.miningplugin.manager;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.data.Backpack;
import igaodev.miningplugin.data.EnchantPickaxe;
import igaodev.miningplugin.data.Pickaxe;
import igaodev.miningplugin.data.User;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class InventoryManager {
    public void AddDefaultItems(Player player) {
        player.getInventory().clear();

        AddDefaultPickaxe(player);
        AddDefaultBackpack(player);
    }

    public void AddDefaultPickaxe(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());

        final Pickaxe pickaxe = user.getPickaxe();
        final int level = pickaxe.getLevel();
        final int experience = pickaxe.getExperience();
        final int blockBreaks = pickaxe.getBlockBreaks();
        final int durability = pickaxe.getDurability();
        final int maxDurability = pickaxe.getMaxDurability();
        final int miningChance = pickaxe.getMiningChance();

        //Encantamentos da picareta
        final EnchantPickaxe enchantPickaxe = pickaxe.getEnchantPickaxe();
        final int efficiencyEnchant = enchantPickaxe.getEfficiencyEnchant();
        final int experienceEnchant = enchantPickaxe.getExperienceEnchant();
        final int fortuneEnchant = enchantPickaxe.getFortuneEnchant();


        ItemStack item = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta metaItem = item.getItemMeta();

        PersistentDataContainer data = metaItem.getPersistentDataContainer();
        data.set(new NamespacedKey(MiningPlugin.getInstance(), "miningItem"), PersistentDataType.STRING, "pickaxe");

        metaItem.displayName(Component.text("§r§bPicareta §7[" + level + "]"));
        metaItem.lore(Arrays.asList(
                Component.text(" §7Utilize esta picareta para"),
                Component.text(" §7minerar os blocos de pedra."),
                Component.text(""),
                Component.text("§fEncantamentos:"),
                Component.text(" §7Eficiência " + efficiencyEnchant),
                Component.text(" §7Fortuna " + fortuneEnchant),
                Component.text(" §7Experiência " + experienceEnchant),
                Component.text(""),
                Component.text("§fInformações:"),
                Component.text(" §7Mineração: " + miningChance + "%"),
                Component.text(" §7Durabilidade: " + durability + "/" + maxDurability),
                Component.text(" §7Nível: " + level),
                Component.text(" §7Experiência: " + experience),
                Component.text(" §7Blocos quebrados: §7" + blockBreaks)
        ));

        metaItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metaItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaItem.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);

        item.setItemMeta(metaItem);

        player.getInventory().setItem(0, item);
    }

    public void AddDefaultBackpack(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final Backpack backpack = user.getBackpack();

        final int miningStone = backpack.getMiningStone();
        final int miningStoneSize = backpack.getMiningStoneSize();
        final int washingStone = backpack.getWashingStone();
        final int washingStoneSize = backpack.getWashingStoneSize();
        final int ore = backpack.getOre();
        final int oreSize = backpack.getOreSize();

        ItemStack item = new ItemStack(Material.CAULDRON);
        ItemMeta metaItem = item.getItemMeta();

        PersistentDataContainer data = metaItem.getPersistentDataContainer();
        data.set(new NamespacedKey(MiningPlugin.getInstance(), "miningItem"), PersistentDataType.STRING, "backpack");

        metaItem.displayName(Component.text("§r§bSacola de Mineração"));
        metaItem.lore(Arrays.asList(
                Component.text(" §7Utilize esta sacola para"),
                Component.text(" §7guardar as suas pedras e minérios."),
                Component.text(""),
                Component.text("§fInformações:"),
                Component.text(" §7Pedras: " + miningStone + "/" + miningStoneSize),
                Component.text(" §7Pedras lavadas: " + washingStone + "/" + washingStoneSize),
                Component.text(" §7Minérios: " + ore + "/" + oreSize)
        ));

        metaItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metaItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(metaItem);

        player.getInventory().setItem(1, item);
    }
}

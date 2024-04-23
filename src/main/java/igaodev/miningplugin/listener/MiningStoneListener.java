package igaodev.miningplugin.listener;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.data.Backpack;
import igaodev.miningplugin.data.Pickaxe;
import igaodev.miningplugin.data.User;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static igaodev.miningplugin.mob.ZombieSpawn.spawnZombie;

public class MiningStoneListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();

        if (block.getType() == Material.STONE) {
            if (!isPluginPickaxe(player)) {
                return;
            }

            final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
            final Pickaxe pickaxe = user.getPickaxe();

            if (pickaxe.isBroken()) {
                player.sendMessage("§cSua picareta esta quebrada!");
                event.setCancelled(true);
                return;
            }

            // Adiciona um bloco quebrado a picareta
            pickaxe.setBlockBreaks(pickaxe.getBlockBreaks() + 1);

            dropMiningStone(player);
            fatigue(player);
            pickaxeDurability(player);

            // Atualiza a picareta do jogador
            MiningPlugin.getInstance().getInventoryManager().AddDefaultPickaxe(player);

            MiningPlugin.getInstance().getBlockManager().addBlock(block.getLocation());
            block.setType(Material.BEDROCK);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        final Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        if (!isPluginPickaxe(player)) {
            return;
        }

        if (clickedBlock.getType() == Material.STONE) {
            final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
            final Pickaxe pickaxe = user.getPickaxe();
            final boolean broken = pickaxe.isBroken();

            if (broken) {
                player.sendMessage("§cSua picareta esta quebrada!");
                event.setCancelled(true);
            }
        }
    }

    private void dropMiningStone(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final Pickaxe pickaxe = user.getPickaxe();
        final int miningChance = pickaxe.getMiningChance();

        Random random = new Random();
        double chance = random.nextDouble() * 100;

        if (chance <= miningChance) {
            final Backpack backpack = user.getBackpack();
            final int miningStone = backpack.getMiningStone();
            final int miningStoneSize = backpack.getMiningStoneSize();

            if (miningStone >= miningStoneSize) {
                player.sendMessage("§cSua sacola de minérios esta cheia!");
            } else {
                backpack.setMiningStone(miningStone + 1);
                MiningPlugin.getInstance().getInventoryManager().AddDefaultBackpack(player);
                player.sendMessage("§aVocê coletou um minério!");
                //spawnZombie(player);
            }
        }
    }

    private void fatigue(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final int energy = user.getEnergy();
        final boolean exhausted = user.isExhausted();

        if (exhausted) {
            return;
        }

        if (energy <= 1) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1200 * 5, 1));
            user.setEnergy(energy - 1);
            user.setExhausted(true);
            player.sendMessage("§cVocê ficou cansado!");
        } else {
            user.setEnergy(energy - 1);
        }
    }

    private void pickaxeDurability(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final Pickaxe pickaxe = user.getPickaxe();
        final int durability = pickaxe.getDurability();

        if (durability <= 1) {
            player.sendMessage("§cSua picareta quebrou!");
            player.playSound(player.getLocation(), "minecraft:entity.item.break", 1, 1);
            pickaxe.setBroken(true);
        }

        pickaxe.setDurability(durability - 1);
        MiningPlugin.getInstance().getInventoryManager().AddDefaultPickaxe(player);
    }

    private boolean isPluginPickaxe(Player player) {
        final ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        if (itemMeta == null) {
            return false;
        }

        final String itemPersistantData = itemMeta.getPersistentDataContainer()
                .get(new NamespacedKey(MiningPlugin.getInstance(), "miningItem"), PersistentDataType.STRING);

        if (itemPersistantData == null) {
            return false;
        }

        return itemPersistantData.equals("pickaxe");
    }
}

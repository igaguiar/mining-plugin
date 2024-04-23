package igaodev.miningplugin.listener;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.menu.BackpackMenu;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class ItemInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!event.hasItem()) {
            return;
        }

        final Action action = event.getAction();
        if (!action.name().contains("RIGHT")) {
            return;
        }

        final String itemPersistantData = event.getItem().getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(MiningPlugin.getInstance(), "miningItem"), PersistentDataType.STRING);

        if (itemPersistantData == null) {
            return;
        }

        // Verificações de qual item foi clicado
        if (itemPersistantData.equals("backpack")) {
            MiningPlugin.getInstance().getViewBackpack().open(BackpackMenu.class, player);
        }
//        else if (itemPersistantData.equals("upgrades")) {
//            FishingPlugin.getInstance().getViewUpgrades().open(UpgradesMenu.class, player);
//        } else if (itemPersistantData.equals("shop")) {
//            FishingPlugin.getInstance().getViewShop().open(ShopMenu.class, player);
//        }

        event.setCancelled(true);
    }
}

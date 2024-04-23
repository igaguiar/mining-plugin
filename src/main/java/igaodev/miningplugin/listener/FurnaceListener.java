package igaodev.miningplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class FurnaceListener implements Listener {

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        event.setBurnTime(100);

        Bukkit.getLogger().info(event.getBlock().toString());
        Bukkit.getLogger().info(event.getFuel().toString());
    }

    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        Bukkit.getLogger().info("FurnaceSmelt");
        Bukkit.getLogger().info(event.getBlock().toString());
        Bukkit.getLogger().info(event.getSource().toString());
        Bukkit.getLogger().info(event.getResult().toString());
    }
}

package igaodev.miningplugin.runnable;

import igaodev.miningplugin.MiningPlugin;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BlockRunnable implements Runnable {

    public BlockRunnable(MiningPlugin plugin) {
        plugin.getServer()
                .getScheduler()
                .runTaskTimer(plugin, this, 0, 20L);
    }

    @Override
    public void run() {
        Map<Location, Long> blocks = MiningPlugin.getInstance().getBlockManager().getBlocks();
        Iterator<Map.Entry<Location, Long>> iterator = blocks.entrySet().iterator();
        long currentTime = System.currentTimeMillis();

        while (iterator.hasNext()) {
            Map.Entry<Location, Long> entry = iterator.next();
            Location location = entry.getKey();
            Long lastBlockTime = entry.getValue();
            if (lastBlockTime + TimeUnit.SECONDS.toMillis(5) > currentTime) {
                continue;
            }

            location.getBlock().setType(Material.STONE);
            iterator.remove(); // Remove o elemento de forma segura durante a iteração
            MiningPlugin.getInstance().getBlockManager().removeBlock(location);
        }
    }
}

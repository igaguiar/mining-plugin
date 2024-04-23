package igaodev.miningplugin.manager;

import org.bukkit.Location;

import java.util.*;

public class BlockManager {

    private final Map<Location, Long> blocks = new HashMap<>();

    public void addBlock(Location location) {
        blocks.put(location, System.currentTimeMillis());
    }

    public void removeBlock(Location location) {
        blocks.remove(location);
    }

    public Map<Location, Long> getBlocks() {
        return blocks;
    }
}

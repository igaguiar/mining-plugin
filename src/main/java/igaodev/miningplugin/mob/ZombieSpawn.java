package igaodev.miningplugin.mob;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class ZombieSpawn {
    public static void spawnZombie(Player player) {
        final World world = Bukkit.getServer().getWorld("world");
        Location location = new Location(world, -2.5, -46, -0.5);
        Zombie zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

        // Impedir que o zumbi se mova
        zombie.setAI(false);

        // Impedir que o zumbi cause dano
        zombie.setCanPickupItems(false);
        zombie.setInvulnerable(true);

        zombie.customName(Component.text("§7Zumbi"));
        zombie.setCustomNameVisible(true);

        player.sendMessage("§aUm Zumbi apareceu!");
    }
}

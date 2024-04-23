package igaodev.miningplugin.listener;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.data.User;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WashingAreaListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());

        // Defina as coordenadas da área específica
        double areaX = 0.5; // coordenada X da área
        double areaZ = 6.5; // coordenada Z da área
        double areaRadius = 2; // raio da área

        Location areaCenter = new Location(world, areaX, playerLocation.getY(), areaZ);

        // Verifica se o jogador está dentro da área
        if (playerLocation.distance(areaCenter) <= areaRadius) {
            user.setWashingArea(true);
        } else {
            user.setWashingArea(false);
        }
    }
}

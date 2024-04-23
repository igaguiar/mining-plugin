package igaodev.miningplugin.listener;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.data.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrafficListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());

        // Se o usuário não existir em cache, cria um novo
        if (user == null) {
            MiningPlugin.getInstance().getUserManager().createUser(player.getUniqueId());
        }

        MiningPlugin.getInstance().getInventoryManager().AddDefaultItems(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        player.sendMessage("§cAté mais!");
    }
}

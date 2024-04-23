package igaodev.miningplugin.runnable;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.data.User;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardRunnable implements Runnable {

    public ScoreboardRunnable(MiningPlugin plugin) {
        plugin.getServer()
                .getScheduler()
                .runTaskTimer(plugin, this, 0, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getScoreboard().getObjective("mining") == null) {
                createNewScoreboard(player);
            } else {
                updateScoreboard(player);
            }
        }
    }

    private void createNewScoreboard(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final int energy = user.getEnergy();
        final int maxEnergy = user.getMaxEnergy();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("mining", Criteria.DUMMY, Component.text("§b§lMINING SIMULATOR"));

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§e ").setScore(2);
        objective.getScore("§e§lNível: §f0").setScore(1);

        Team team = scoreboard.registerNewTeam("team");
        String teamKey = Component.text("§e").content();

        team.addEntry(teamKey);
        team.prefix(Component.text("§e§lEnergia: §f" + energy + "/" + maxEnergy));
        objective.getScore(teamKey).setScore(0);

        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player) {
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final int energy = user.getEnergy();
        final int maxEnergy = user.getMaxEnergy();

        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam("team");

        team.prefix(Component.text("§e§lEnergia: §f" + energy + "/" + maxEnergy));

        player.setScoreboard(scoreboard);
    }
}

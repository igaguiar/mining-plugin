package igaodev.miningplugin;

import igaodev.miningplugin.listener.*;
import igaodev.miningplugin.manager.BlockManager;
import igaodev.miningplugin.manager.InventoryManager;
import igaodev.miningplugin.manager.UserManager;
import igaodev.miningplugin.menu.BackpackMenu;
import igaodev.miningplugin.runnable.BlockRunnable;
import igaodev.miningplugin.runnable.ScoreboardRunnable;
import lombok.Getter;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MiningPlugin extends JavaPlugin {

    UserManager userManager;
    InventoryManager inventoryManager;

    BlockManager blockManager;

    private ViewFrame viewBackpack;

    @Override
    public void onEnable() {
        this.userManager = new UserManager();
        this.inventoryManager = new InventoryManager();
        this.blockManager = new BlockManager();

        this.viewBackpack = ViewFrame.create(this)
                .with(new BackpackMenu())
                .register();

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MiningStoneListener(), this);
        getServer().getPluginManager().registerEvents(new TrafficListener(), this);
        getServer().getPluginManager().registerEvents(new WashingAreaListener(), this);
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        getServer().getPluginManager().registerEvents(new FurnaceListener(), this);

        new BlockRunnable(this);
        new ScoreboardRunnable(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MiningPlugin getInstance() {
        return getPlugin(MiningPlugin.class);
    }
}

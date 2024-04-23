package igaodev.miningplugin.menu;

import igaodev.miningplugin.MiningPlugin;
import igaodev.miningplugin.data.Backpack;
import igaodev.miningplugin.data.User;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.context.SlotClickContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BackpackMenu extends View {

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title("Sacola de Mineração");
        config.size(3); // rows
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(RenderContext render) {
        //Pedras mineradas
        miningStones(render);

        //Pedras lavadas
        washingStones(render);
    }

    @Override
    public void onClick(SlotClickContext slotClick) {
        //slotClick.getPlayer().sendMessage("Clicked on slot " + slotClick.getSlot());

        if (slotClick.getSlot() == 11) {
            final User user = MiningPlugin.getInstance().getUserManager().getUser(slotClick.getPlayer().getUniqueId());
            final boolean washingArea = user.isWashingArea();
            final Backpack backpack = user.getBackpack();
            final int miningStone = backpack.getMiningStone();

            if (!washingArea) {
                slotClick.getPlayer().sendMessage("§cVocê não está no local para lavar as pedras!");
                return;
            }

            if (miningStone == 0) {
                slotClick.getPlayer().sendMessage("§cVocê não possui pedras para lavar!");
                slotClick.closeForPlayer();
            } else {
                slotClick.getPlayer().sendMessage("§aVocê lavou " + miningStone + " pedras!");
                backpack.setMiningStone(0);
                backpack.setWashingStone(miningStone);
                slotClick.closeForPlayer();
            }
        }
//
//        if (slotClick.getSlot() == 15) {
//            final Fisherman fisherman = FishingPlugin
//                    .getInstance().getFishermanManager().getFisherman(slotClick.getPlayer());
//            final Boolean autosell = fisherman.getAutoSell().getEnabled();
//            final Boolean autosellActive = fisherman.getAutoSell().getActive();
//
//            if (autosell) {
//                if (autosellActive) {
//                    fisherman.getAutoSell().setActive(false);
//                    FishingPlugin.getInstance().getAutoSellManager().removePlayerAutoSell(slotClick.getPlayer());
//                    slotClick.getPlayer().sendMessage("§cVocê desativou a venda automática!");
//
//                    slotClick.closeForPlayer();
//                } else {
//                    fisherman.getAutoSell().setActive(true);
//                    FishingPlugin.getInstance().getAutoSellManager().addPlayerAutoSell(slotClick.getPlayer());
//                    slotClick.getPlayer().sendMessage("§aVocê ativou a venda automática!");
//
//                    slotClick.closeForPlayer();
//                }
//            } else {
//                slotClick.getPlayer().sendMessage("§cVocê não possui a venda automática habilitada!");
//                slotClick.closeForPlayer();
//            }
//        }
    }

    private void miningStones(RenderContext render) {
        final Player player = render.getPlayer();
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final Backpack backpack = user.getBackpack();
        final int miningStone = backpack.getMiningStone();
        final int miningStoneSize = backpack.getMiningStoneSize();

        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta metaItem = item.getItemMeta();

        metaItem.displayName(Component.text("§cPedras Mineradas"));
        metaItem.lore(Arrays.asList(
                Component.text("§fQuantidade: " + miningStone + "/" + miningStoneSize),
                Component.text(""),
                Component.text("§7Clique aqui para"),
                Component.text("§7lavar as suas pedras!")
        ));

        item.setItemMeta(metaItem);

        render.slot(2, 3, item);
    }

    private void washingStones(RenderContext render) {
        final Player player = render.getPlayer();
        final User user = MiningPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        final Backpack backpack = user.getBackpack();
        final int miningStone = backpack.getMiningStone();
        final int miningStoneSize = backpack.getMiningStoneSize();

        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta metaItem = item.getItemMeta();

        metaItem.displayName(Component.text("§cPedras Lavadas"));
        metaItem.lore(Arrays.asList(
                Component.text(" §7Você não possui peixes"),
                Component.text(" §7pescados para vender!"),
                Component.text(""),
                Component.text("§fUtilize a sua vara"),
                Component.text("§fpara pescar!")
        ));

        item.setItemMeta(metaItem);

        render.slot(2, 4, item);
    }
}

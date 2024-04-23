package igaodev.miningplugin.data;

import igaodev.miningplugin.MiningPlugin;
import lombok.Data;

@Data
public class Backpack {
    private int miningStone;
    private int miningStoneSize;

    private int washingStone;
    private int washingStoneSize;

    private int ore;
    private int oreSize;

    public Backpack() {
        this.miningStone = 0;
        this.miningStoneSize = 5;
        this.washingStone = 0;
        this.washingStoneSize = 5;
        this.ore = 0;
        this.oreSize = 5;
    }
}

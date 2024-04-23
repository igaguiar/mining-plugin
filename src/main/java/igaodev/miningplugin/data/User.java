package igaodev.miningplugin.data;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID uniqueId;
    private Pickaxe pickaxe;
    private Backpack backpack;

    private int energy;
    private int maxEnergy;
    private boolean isExhausted;
    private boolean isWashingArea;

    public User(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.pickaxe = new Pickaxe();
        this.backpack = new Backpack();
        this.energy = 10;
        this.maxEnergy = 10;
        this.isExhausted = false;
        this.isWashingArea = false;
    }
}

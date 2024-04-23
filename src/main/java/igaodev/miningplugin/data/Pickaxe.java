package igaodev.miningplugin.data;

import lombok.Data;

@Data
public class Pickaxe {
    private EnchantPickaxe enchantPickaxe;
    private int level;
    private int experience;
    private int durability;
    private int maxDurability;
    private int miningChance;
    private boolean isBroken;
    private String skinType;

    private int blockBreaks;

    public Pickaxe() {
        this.enchantPickaxe = new EnchantPickaxe();
        this.level = 0;
        this.experience = 0;
        this.durability = 10;
        this.maxDurability = 10;
        this.miningChance = 35; //35% de chance
        this.isBroken = false;
        this.skinType = "Nenhuma";
        this.blockBreaks = 0;
    }
}

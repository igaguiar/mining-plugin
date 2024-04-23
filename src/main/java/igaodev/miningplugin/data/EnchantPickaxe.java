package igaodev.miningplugin.data;

import lombok.Data;

@Data
public class EnchantPickaxe {
    private int fortuneEnchant;
    private int experienceEnchant;
    private int efficiencyEnchant;

    private boolean unbreakingEnchant;

    public EnchantPickaxe() {
        this.fortuneEnchant = 0;
        this.experienceEnchant = 0;
        this.efficiencyEnchant = 0;

        this.unbreakingEnchant = false;
    }
}

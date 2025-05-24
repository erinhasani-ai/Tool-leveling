package com.ai.Tool-Leveling;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "powerenchanter")
public class PowerEnchantConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    @ConfigEntry.Comment("XP cost per enchantment level you add or raise above vanilla max.")
    public int xpCostPerLevel = 1;
}
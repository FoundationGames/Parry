package io.github.foundationgames.parry.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Config(name = "parry")
public class ParryConfig implements ConfigData {
    public double default_multiplier = 0.50;
    public boolean consume_animation = true;
    public boolean prioritize_shield = false;
    public OverrideValue[] overrides = new OverrideValue[] {};

    public static class OverrideValue {
        public String item;
        public double multiplier;

        public Item getItem() {
            return Registry.ITEM.get(Identifier.tryParse(this.item));
        }
    }
}

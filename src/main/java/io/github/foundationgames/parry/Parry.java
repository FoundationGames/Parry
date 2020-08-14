package io.github.foundationgames.parry;

import io.github.foundationgames.parry.config.ParryConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class Parry implements ModInitializer {
    @Override
    public void onInitialize() {
        AutoConfig.register(ParryConfig.class, GsonConfigSerializer::new);
    }
}

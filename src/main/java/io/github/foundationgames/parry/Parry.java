package io.github.foundationgames.parry;

import io.github.foundationgames.parry.config.ParryConfig;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public class Parry implements ModInitializer {
    private static ParryConfig config;

    @Override
    public void onInitialize() {
        initConfig();
    }

    private static void initConfig() {
        try {
            config = ParryConfig.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading Parry config!", e);
        }
    }

    public static ParryConfig getConfig() {
        if (config == null) {
            initConfig();
        }
        return config;
    }
}

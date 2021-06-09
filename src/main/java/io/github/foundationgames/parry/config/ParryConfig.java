package io.github.foundationgames.parry.config;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Files;

public class ParryConfig {
    public double default_multiplier = 0.50;
    public boolean consume_animation = true;
    public boolean prioritize_shield = false;
    public OverrideValue[] overrides = new OverrideValue[] {};

    public static ParryConfig load() throws IOException {
        var configFile = FabricLoader.getInstance().getConfigDir().resolve("parry.json");
        var gson = new Gson();
        if (!Files.exists(configFile)) {
            save(new ParryConfig());
        }
        return gson.fromJson(Files.newBufferedReader(configFile), ParryConfig.class);
    }

    public static void save(ParryConfig config) throws IOException {
        var configFile = FabricLoader.getInstance().getConfigDir().resolve("parry.json");
        var gson = new Gson();
        var writer = gson.newJsonWriter(Files.newBufferedWriter(configFile));
        writer.setIndent("    ");
        gson.toJson(gson.toJsonTree(config, ParryConfig.class), writer);
        writer.close();
    }

    public static class OverrideValue {
        public String item;
        public double multiplier;

        public Item getItem() {
            return Registry.ITEM.get(Identifier.tryParse(this.item));
        }
    }
}

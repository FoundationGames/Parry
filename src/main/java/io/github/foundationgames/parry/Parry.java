package io.github.foundationgames.parry;

import io.github.foundationgames.parry.config.ParryConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Parry implements ModInitializer {
    @Override
    public void onInitialize() {
        AutoConfig.register(ParryConfig.class, GsonConfigSerializer::new);
        Registry.ITEM.forEach((item) -> {
            if(item instanceof SwordItem) {
                FabricModelPredicateProviderRegistry.register(item, new Identifier("parrying"), (stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
            }
        });
    }
}

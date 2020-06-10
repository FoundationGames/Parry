package io.github.foundationgames.parry;

import io.github.foundationgames.parry.util.Utilz;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Logger;

public class Parry implements ModInitializer {

    public static final Logger LOGGER = FabricLoader.INSTANCE.getLogger();

    @Override
    public void onInitialize() {
        Registry.ITEM.forEach((item) -> {
            if(item instanceof SwordItem) {
                FabricModelPredicateProviderRegistry.register(item, new Identifier("parrying"), (stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
            }
        });
    }
}

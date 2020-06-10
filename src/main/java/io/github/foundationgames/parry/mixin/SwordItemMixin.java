package io.github.foundationgames.parry.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(SwordItem.class)
public class SwordItemMixin extends Item {
    public SwordItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    public void init(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
    }
}

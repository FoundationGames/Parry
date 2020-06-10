package io.github.foundationgames.parry.mixin;

import io.github.foundationgames.parry.config.ParryConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public ItemStack activeItemStack;
    @Shadow public abstract boolean isUsingItem();
    @Shadow public abstract void applyDamage(DamageSource source, float amount);

    @Inject(at = @At(value = "HEAD"), method = "isBlocking", cancellable = true)
    public void nullifySwordBlock(CallbackInfoReturnable<Boolean> cir) {
        Item item = this.activeItemStack.getItem();
        if(item instanceof SwordItem) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "damage", cancellable = true)
    public void applySwordBlockProtection(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Item item = this.activeItemStack.getItem();
        if(item instanceof SwordItem && this.isUsingItem()) {
            if(source != DamageSource.FALL && source != DamageSource.FALLING_BLOCK && source != DamageSource.DROWN && source != DamageSource.IN_FIRE && source != DamageSource.ON_FIRE && source != DamageSource.MAGIC && source != DamageSource.HOT_FLOOR && source != DamageSource.DRAGON_BREATH && source != DamageSource.WITHER && source != DamageSource.CRAMMING) {
                ParryConfig cfg = AutoConfig.getConfigHolder(ParryConfig.class).getConfig();
                double multiplier = cfg.defaultParryDamageModifier;
                for(ParryConfig.OverrideValue v : cfg.parryDamageOverrides) {
                    if(item == v.getItem()) multiplier = v.multiplier;
                }
                amount *= (float)multiplier;
                this.applyDamage(source, amount);
                cir.setReturnValue(true);
            }
        }
    }

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
}

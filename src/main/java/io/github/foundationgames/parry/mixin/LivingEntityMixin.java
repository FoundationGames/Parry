package io.github.foundationgames.parry.mixin;

import io.github.foundationgames.parry.Parry;
import io.github.foundationgames.parry.config.ParryConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow protected ItemStack activeItemStack;
    @Shadow public abstract boolean isUsingItem();
    @Shadow public abstract boolean blockedByShield(DamageSource source);

    private DamageSource parry$cachedSource;
    private boolean parry$appearBlocking = false;

    @Inject(at = @At(value = "HEAD"), method = "isBlocking", cancellable = true)
    public void parry$fakeShieldBlocking(CallbackInfoReturnable<Boolean> cir) {
        var item = this.activeItemStack.getItem();
        if(item instanceof SwordItem) {
            cir.setReturnValue(parry$appearBlocking);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "damage", cancellable = true)
    public void parry$cacheDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.parry$cachedSource = source;
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), index = 2)
    private float parry$applySwordBlockProtection(float old) {
        var item = this.activeItemStack.getItem();
        parry$appearBlocking = true;
        if(item instanceof SwordItem && this.isUsingItem() && this.blockedByShield(parry$cachedSource)) {
            var cfg = Parry.getConfig();
            double multiplier = cfg.default_multiplier;
            for(ParryConfig.OverrideValue v : cfg.overrides) {
                if(item == v.getItem()) multiplier = v.multiplier;
            }
            System.out.println(old +" into "+old * multiplier);
            old *= multiplier;
        }
        parry$appearBlocking = false;
        return old;
    }

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
}

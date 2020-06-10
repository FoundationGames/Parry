package io.github.foundationgames.parry.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
        System.out.println("calld");
        if(item instanceof SwordItem && this.isUsingItem()) {
            System.out.println("usin");
            if(source != DamageSource.FALL && source != DamageSource.FALLING_BLOCK && source != DamageSource.DROWN && source != DamageSource.IN_FIRE && source != DamageSource.ON_FIRE && source != DamageSource.MAGIC && source != DamageSource.HOT_FLOOR && source != DamageSource.DRAGON_BREATH && source != DamageSource.WITHER && source != DamageSource.CRAMMING) {
                amount *= 0.50;
                this.applyDamage(source, amount);
                System.out.println("blokd");
                cir.setReturnValue(true);
            }
        }
    }

    /*-----------------------------------------------------------------*/

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

}

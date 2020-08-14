package io.github.foundationgames.parry.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccess {
    @Invoker(value = "tryUseTotem")
    boolean doTryUseTotem(DamageSource source);

    @Invoker(value = "getDeathSound")
    SoundEvent doGetDeathSound();

    @Invoker(value = "getSoundVolume")
    float doGetSoundVolume();

    @Invoker(value = "getSoundPitch")
    float doGetSoundPitch();

    @Invoker(value = "playHurtSound")
    void doPlayHurtSound(DamageSource source);

    @Accessor(value = "lastDamageSource")
    void setLastDamageSource(DamageSource source);

    @Accessor(value = "lastDamageTime")
    void setLastDamageTime(long time);
}

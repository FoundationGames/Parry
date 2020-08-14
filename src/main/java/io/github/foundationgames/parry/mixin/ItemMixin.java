package io.github.foundationgames.parry.mixin;

import io.github.foundationgames.parry.config.ParryConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At(value = "HEAD"), method = "use", cancellable = true)
    public void useSword(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if(user.getStackInHand(hand).getItem() instanceof SwordItem) {
            ParryConfig cfg = AutoConfig.getConfigHolder(ParryConfig.class).getConfig();
            ItemStack itemStack = user.getStackInHand(hand);
            if(cfg.prioritize_shield && user.getStackInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem) {
                user.setCurrentHand(Hand.OFF_HAND);
                cir.cancel();
            }
            user.setCurrentHand(hand);
            user.setSprinting(false);
            if(cfg.consume_animation) cir.setReturnValue(TypedActionResult.consume(itemStack));
            else cir.setReturnValue(TypedActionResult.pass(itemStack));
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getMaxUseTime", cancellable = true)
    public void swordUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if(stack.getItem() instanceof SwordItem) {
            cir.setReturnValue(72000);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getUseAction", cancellable = true)
    public void useAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if(stack.getItem() instanceof SwordItem) {
            cir.setReturnValue(UseAction.BLOCK);
        }
    }
}

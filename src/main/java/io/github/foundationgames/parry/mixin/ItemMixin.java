package io.github.foundationgames.parry.mixin;

import io.github.foundationgames.parry.Parry;
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
    public void parry$allowSwordUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if(user.getStackInHand(hand).getItem() instanceof SwordItem) {
            var cfg = Parry.getConfig();
            var stack = user.getStackInHand(hand);
            if(cfg.prioritize_shield && user.getStackInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem) {
                user.setCurrentHand(Hand.OFF_HAND);
                cir.cancel();
            }
            user.setCurrentHand(hand);
            user.setSprinting(false);
            if(cfg.consume_animation) cir.setReturnValue(TypedActionResult.consume(stack));
            else cir.setReturnValue(TypedActionResult.pass(stack));
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getMaxUseTime", cancellable = true)
    public void parry$applySwordUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if(stack.getItem() instanceof SwordItem) {
            cir.setReturnValue(72000);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getUseAction", cancellable = true)
    public void parry$returnBlockUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if(stack.getItem() instanceof SwordItem) {
            cir.setReturnValue(UseAction.BLOCK);
        }
    }
}

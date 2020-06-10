package io.github.foundationgames.parry.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends PlayerEntity {



    /*------------------------------------------------------------------------------------*/

    public PlayerEntityMixin(World world, BlockPos blockPos, GameProfile gameProfile) {
        super(world, blockPos, gameProfile);
    }
    @Override
    public boolean isSpectator() { return false; }
    @Override
    public boolean isCreative() { return false; }
}

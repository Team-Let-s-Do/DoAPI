package de.cristelknight.doapi.forge.mixin.sign;

import de.cristelknight.doapi.forge.terraform.sign.TerraformSign;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
@Mixin(BlockEntityType.class)
public class MixinBlockEntityType {
	@Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> info) {
		Block block = state.getBlock();

		if (block instanceof TerraformSign) {
			if (BlockEntityType.HANGING_SIGN.equals(this)) {
				if (!(block instanceof CeilingHangingSignBlock || block instanceof WallHangingSignBlock)) {
					return;
				}
			} else if (!BlockEntityType.SIGN.equals(this)) {
				return;
			}

			info.setReturnValue(true);
		}
	}
}

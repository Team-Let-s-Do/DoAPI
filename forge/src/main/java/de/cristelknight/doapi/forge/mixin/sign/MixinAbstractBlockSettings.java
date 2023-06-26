package de.cristelknight.doapi.forge.mixin.sign;

import de.cristelknight.doapi.forge.terraform.sign.BlockSettingsLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.block.state.BlockBehaviour;

@Mixin(BlockBehaviour.Properties.class)
public class MixinAbstractBlockSettings implements BlockSettingsLock {
	@Unique
	private boolean terraform$locked = false;

	@Inject(method = "sounds", at = @At("HEAD"), cancellable = true)
	private void terraform$preventSoundsOverride(CallbackInfoReturnable<BlockBehaviour.Properties> ci) {
		if (this.terraform$locked) {
			ci.setReturnValue((BlockBehaviour.Properties) (Object) this);
			this.terraform$locked = false;
		}
	}

	@Override
	public void lock() {
		this.terraform$locked = true;
	}
}

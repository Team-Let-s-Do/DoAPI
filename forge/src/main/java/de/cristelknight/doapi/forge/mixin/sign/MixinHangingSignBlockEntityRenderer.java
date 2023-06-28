package de.cristelknight.doapi.forge.mixin.sign;

import de.cristelknight.doapi.forge.terraform.sign.TerraformSign;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.resources.model.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(HangingSignRenderer.class)
public abstract class MixinHangingSignBlockEntityRenderer extends MixinSignBlockEntityRenderer {
	@Inject(method = "getSignMaterial", at = @At("HEAD"), cancellable = true)
	private void getHangingSignTextureId(CallbackInfoReturnable<Material> ci) {
		if (this.terraform$renderedBlockEntity != null) {
			if (this.terraform$renderedBlockEntity.getBlockState().getBlock() instanceof TerraformSign signBlock) {
				ci.setReturnValue(new Material(Sheets.SIGN_SHEET, signBlock.getTexture()));
			}
		}
	}
}

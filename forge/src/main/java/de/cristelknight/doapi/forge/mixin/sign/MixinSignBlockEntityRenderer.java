package de.cristelknight.doapi.forge.mixin.sign;

import de.cristelknight.doapi.forge.terraform.sign.TerraformSign;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SignRenderer.class)
public class MixinSignBlockEntityRenderer {
	@ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/Sheets;getSignMaterial(Lnet/minecraft/world/level/block/state/properties/WoodType;)Lnet/minecraft/client/resources/model/Material;"))
	private Material getSignTextureId(Material spriteIdentifier, SignBlockEntity signBlockEntity) {
		if (signBlockEntity.getBlockState().getBlock() instanceof TerraformSign) {
			return new Material(Sheets.SIGN_SHEET, ((TerraformSign) signBlockEntity.getBlockState().getBlock()).getTexture());
		}
		return spriteIdentifier;
	}
}

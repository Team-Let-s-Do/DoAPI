package de.cristelknight.doapi.forge.mixin.sign;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.forge.terraform.sign.TerraformSign;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignRenderer.class)
public abstract class MixinSignBlockEntityRenderer {
	@Unique
	protected SignBlockEntity terraform$renderedBlockEntity;

	@WrapOperation(
			method = "render(Lnet/minecraft/block/entity/SignBlockEntity;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/block/BlockState;Lnet/minecraft/block/AbstractSignBlock;Lnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer;renderSign(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model;)V")
	)
	@SuppressWarnings("unused")
	private void setRenderedBlockEntity(SignRenderer instance, PoseStack matrices, MultiBufferSource verticesProvider, int light, int overlay, WoodType type, Model model, Operation<Void> original, SignBlockEntity signBlockEntity) {
		this.terraform$renderedBlockEntity = signBlockEntity;
		original.call(instance, matrices, verticesProvider, light, overlay, type, model);
		this.terraform$renderedBlockEntity = null;
	}

	@Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
	private void getSignTextureId(CallbackInfoReturnable<Material> ci) {
		if (this.terraform$renderedBlockEntity != null) {
			if (this.terraform$renderedBlockEntity.getBlockState().getBlock() instanceof TerraformSign signBlock) {
				ci.setReturnValue(new Material(Sheets.SIGN_SHEET, signBlock.getTexture()));
			}
		}
	}
}

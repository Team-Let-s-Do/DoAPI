package de.cristelknight.doapi.forge.mixin.sign;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.cristelknight.doapi.forge.terraform.sign.TerraformSign;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignEditScreen.class)
public class MixinSignEditScreen {

	@WrapOperation(
			method = "renderSignBackground",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Sheets;getSignMaterial(Lnet/minecraft/world/level/block/state/properties/WoodType;)Lnet/minecraft/client/resources/model/Material;")
	)
	@SuppressWarnings("unused")
	private Material getTerraformSignTextureId(WoodType type, Operation<Material> original, GuiGraphics drawContext, BlockState state) {
		if (state.getBlock() instanceof TerraformSign signBlock) {
			return new Material(Sheets.SIGN_SHEET, signBlock.getTexture());
		}

		return original.call(type);
	}


}

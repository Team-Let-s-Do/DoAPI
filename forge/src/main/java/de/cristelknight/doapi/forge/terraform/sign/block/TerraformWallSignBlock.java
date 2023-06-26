package de.cristelknight.doapi.forge.terraform.sign.block;

import de.cristelknight.doapi.forge.terraform.sign.BlockSettingsLock;
import de.cristelknight.doapi.forge.terraform.sign.TerraformSign;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	private final ResourceLocation texture;

	public TerraformWallSignBlock(ResourceLocation texture, Properties settings) {
		super(BlockSettingsLock.lock(settings), WoodType.OAK); //TODO: take a look at this again
		this.texture = texture;
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
}

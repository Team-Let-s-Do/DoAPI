package de.cristelknight.doapi.terraform;

import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class TerraformSignHelper {

    public static Block getSign(ResourceLocation signTextureId){
        return DoApiExpectPlatform.getSign(signTextureId);
    }

    public static Block getWallSign(ResourceLocation signTextureId){
        return DoApiExpectPlatform.getWallSign(signTextureId);
    }

    public static void regsterSignSprite(ResourceLocation signTextureId){
        DoApiExpectPlatform.addSignSprite(signTextureId);
    }

}

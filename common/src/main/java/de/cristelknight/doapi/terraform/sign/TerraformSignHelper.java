package de.cristelknight.doapi.terraform.sign;

import de.cristelknight.doapi.DoApiCommonEP;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class TerraformSignHelper {

    public static Block getSign(ResourceLocation signTextureId){
        return DoApiCommonEP.getSign(signTextureId);
    }

    public static Block getWallSign(ResourceLocation signTextureId){
        return DoApiCommonEP.getWallSign(signTextureId);
    }

    public static Block getHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId){
        return DoApiCommonEP.getHangingSign(hangingSignTextureId, hangingSignGuiTextureId);
    }

    public static Block getWallHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId){
        return DoApiCommonEP.getWallHangingSign(hangingSignTextureId, hangingSignGuiTextureId);
    }

    public static void regsterSignSprite(ResourceLocation signTextureId){
        DoApiCommonEP.addSignSprite(signTextureId);
    }

}

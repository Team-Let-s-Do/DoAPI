package de.cristelknight.doapi;

import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Map;


public class DoApiCommonEP {



    @ExpectPlatform
    public static void addSignSprite(ResourceLocation signTextureId){
        throw new AssertionError();
    }
    @ExpectPlatform
    public static Block getSign(ResourceLocation signTextureId){
        throw new AssertionError();
    }
    @ExpectPlatform
    public static Block getWallSign(ResourceLocation signTextureId){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Block getHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Block getWallHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId){
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks){
        throw new AssertionError();
    }
    @ExpectPlatform
    public static void registerBoatType(ResourceLocation resourceLocation, TerraformBoatType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Map<ResourceLocation, Boolean> getAllDoApiBoatTypeNamesAndRaft(){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Boat createBoat(ResourceLocation boatTypeName, Level world, double x, double y, double z, boolean chest) {
        throw new AssertionError();
    }
}

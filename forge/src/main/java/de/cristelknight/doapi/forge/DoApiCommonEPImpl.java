package de.cristelknight.doapi.forge;

import de.cristelknight.doapi.forge.common.registry.BurningBlockRegistry;
import de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatEntity;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformChestBoatEntity;
import de.cristelknight.doapi.forge.terraform.sign.SpriteIdentifierRegistry;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformHangingSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformWallHangingSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformWallSignBlock;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.HashMap;
import java.util.Map;

public class DoApiCommonEPImpl {

    public static void registerBoatType(ResourceLocation resourceLocation, TerraformBoatType type) {
        TerraformBoatTypeRegistry.register(resourceLocation, type);
    }

    public static Boat createBoat(ResourceLocation boatTypeName, Level world, double x, double y, double z, boolean chest) {
        TerraformBoatType boatType = TerraformBoatTypeRegistry.get(boatTypeName);
        Boat boatEntity;
        if (chest) {
            TerraformChestBoatEntity chestBoat = new TerraformChestBoatEntity(world, x, y, z);
            chestBoat.setTerraformBoat(boatType);
            boatEntity = chestBoat;
        } else {
            TerraformBoatEntity boat = new TerraformBoatEntity(world, x, y, z);
            boat.setTerraformBoat(boatType);
            boatEntity = boat;
        }
        return boatEntity;
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        BurningBlockRegistry.add(burnOdd, igniteOdd, blocks);
    }

    public static void addSignSprite(ResourceLocation signTextureId) {
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(signTextureId);
    }

    public static Map<ResourceLocation, Boolean> getAllDoApiBoatTypeNamesAndRaft() {
        Map<ResourceLocation, Boolean> boats = new HashMap<>();
        for(Map.Entry<ResourceLocation, TerraformBoatType> entry : TerraformBoatTypeRegistry.entrySet()){
            boats.put(entry.getKey(), entry.getValue().isRaft());
        }
        return boats;
    }

    public static Block getSign(ResourceLocation signTextureId) {
        return new TerraformSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getWallSign(ResourceLocation signTextureId) {
        return new TerraformWallSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return new TerraformHangingSignBlock(hangingSignTextureId, hangingSignGuiTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN));
    }

    public static Block getWallHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return new TerraformWallHangingSignBlock(hangingSignTextureId, hangingSignGuiTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN));
    }

}

package de.cristelknight.doapi.forge;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.forge.registry.BurningBlockRegistry;
import de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatEntity;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformChestBoatEntity;
import de.cristelknight.doapi.forge.terraform.sign.SpriteIdentifierRegistry;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformHangingSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformWallHangingSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformWallSignBlock;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.HashMap;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DoApiExpectPlatformImpl {

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
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

    public static <T> List<Pair<List<String>, T>> findAPIs(Class<T> returnClazz, String name, Class<?> annotationClazz) {
        return ApiFinder.scanForAPIs(annotationClazz, returnClazz);
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

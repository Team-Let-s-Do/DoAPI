package de.cristelknight.doapi.fabric;

import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Set;

public class DoApiExpectPlatformImpl {

    public static void register(ResourceLocation boatTypeName, TerraformBoatType type) {
        com.terraformersmc.terraform.boat.api.TerraformBoatType type1 = new com.terraformersmc.terraform.boat.api.TerraformBoatType.Builder().item(type.getItem()).chestItem(type.getChestItem()).planks(type.getPlanks()).build();

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, boatTypeName, type1);
    }


    public static Boat createBoat(ResourceLocation boatTypeName, Level world, double x, double y, double z, boolean chest) {
        com.terraformersmc.terraform.boat.api.TerraformBoatType boatType = TerraformBoatTypeRegistry.INSTANCE.get(boatTypeName);
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

    public static Set<ResourceLocation> getAllBoatTypeNames() {
        return TerraformBoatTypeRegistry.INSTANCE.keySet();
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();
        for(Block b : blocks) registry.add(b, burnOdd, igniteOdd);
    }

    public static Block getSign(ResourceLocation signTextureId) {
        return new TerraformSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getWallSign(ResourceLocation signTextureId) {
        return new TerraformWallSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static void addSignSprite(ResourceLocation signTextureId) {
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, signTextureId));
    }
}

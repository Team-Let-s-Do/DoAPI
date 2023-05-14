package de.cristelknight.doapi.test;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.DoApiExpectPlatform;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Test {
    
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(DoApi.MOD_ID, Registries.ITEM);


    protected static final String CUSTOM_BOAT_ID = "palm_raft";
    protected static final String CUSTOM_CHEST_BOAT_ID = "palm_chest_raft";

    private static final ResourceLocation BOAT_TYPE_KEY = new ResourceLocation(DoApi.MOD_ID, "palm");

    protected static final ResourceLocation SIGN_TEXTURE_ID = new ResourceLocation(DoApi.MOD_ID, "entity/signs/custom");
    protected static final ResourceLocation HANGING_SIGN_TEXTURE_ID = new ResourceLocation(DoApi.MOD_ID, "entity/signs/hanging/custom");
    protected static final ResourceLocation HANGING_SIGN_GUI_TEXTURE_ID = new ResourceLocation(DoApi.MOD_ID, "textures/gui/hanging_signs/custom");
    
    public static void onInitialize() {

        // Boats
        RegistrySupplier<Item> boatItem = TerraformBoatItemHelper.registerBoatItem(ITEMS, CUSTOM_BOAT_ID, BOAT_TYPE_KEY, false);
        RegistrySupplier<Item> chestBoatItem = TerraformBoatItemHelper.registerBoatItem(ITEMS, CUSTOM_CHEST_BOAT_ID, BOAT_TYPE_KEY, true);
        

        TerraformBoatType boat = new TerraformBoatType.Builder()
                .item(boatItem)
                .chestItem(chestBoatItem)
                .raft()
                .build();

        DoApiExpectPlatform.registerBoatType(BOAT_TYPE_KEY, boat);
        

        /* Signs
        Block sign = new TerraformSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_SIGN));
        Block wallSign = new TerraformWallSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN));

        Item signItem = new SignItem(new Item.Settings().maxCount(16), sign, wallSign);

         */
    }
    
}

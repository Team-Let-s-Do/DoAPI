package de.cristelknight.doapi.client;

import de.cristelknight.doapi.client.render.block.StandardBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.FlowerBoxRenderer;
import de.cristelknight.doapi.client.render.block.storage.FlowerPotBigRenderer;
import de.cristelknight.doapi.client.render.block.storage.api.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.entity.ChairRenderer;
import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import de.cristelknight.doapi.common.registry.DoApiBlocks;
import de.cristelknight.doapi.common.registry.DoApiEntityTypes;
import de.cristelknight.doapi.common.registry.DoApiStorageTypeRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class DoApiClient {
    public static void onClientInit() {
        RenderTypeRegistry.register(RenderType.cutout(), DoApiBlocks.STANDARD.get(), DoApiBlocks.WALL_STANDARD.get()
        );

        registerBlockEntityRenderers();
        registerStorageTypeRenderers();
    }

    public static void preClientInit(){
        registerModelLayers();
        registerEntityRenderers();
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(DoApiEntityTypes.CHAIR, ChairRenderer::new);
    }

    public static void registerBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(DoApiBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(DoApiBlockEntityTypes.STANDARD.get(), StandardBlockEntityRenderer::new);
    }

    public static void registerModelLayers() {
        EntityModelLayerRegistry.register(StandardBlockEntityRenderer.LAYER_LOCATION, StandardBlockEntityRenderer::createBodyLayer);
    }

    public static void registerStorageTypeRenderers(){
        StorageBlockEntityRenderer.registerStorageType(DoApiStorageTypeRegistry.FLOWER_POT_BIG, new FlowerPotBigRenderer());
        StorageBlockEntityRenderer.registerStorageType(DoApiStorageTypeRegistry.FLOWER_BOX, new FlowerBoxRenderer());
    }
}

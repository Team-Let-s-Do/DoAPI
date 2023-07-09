package de.cristelknight.doapi.client;

import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.entity.ChairRenderer;
import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import de.cristelknight.doapi.common.registry.DoApiEntityTypes;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;

public class DoApiClient {

    public static void onClientInit(){
        registerBlockEntityRenderers();
    }

    public static void preClientInit(){
        registerEntityRenderers();
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(DoApiEntityTypes.CHAIR, ChairRenderer::new);
    }

    public static void registerBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(DoApiBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
    }

}

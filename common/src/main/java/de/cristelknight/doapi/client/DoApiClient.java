package de.cristelknight.doapi.client;

import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.networking.DoApiMessages;
import de.cristelknight.doapi.registry.DoApiBlockEntityTypes;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;

public class DoApiClient {

    public static void onClientInit(){
        DoApiMessages.registerS2CPackets();
        BlockEntityRendererRegistry.register(DoApiBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
    }

}

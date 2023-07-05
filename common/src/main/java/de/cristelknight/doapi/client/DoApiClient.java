package de.cristelknight.doapi.client;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.entity.ChairRenderer;
import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import de.cristelknight.doapi.common.registry.DoApiEntityTypes;
import de.cristelknight.doapi.terraform.sign.TerraformSignHelper;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.minecraft.resources.ResourceLocation;

public class DoApiClient {

    public static void onClientInit(){
        registerBlockEntityRenderers();
    }

    public static void preClientInit(){
        registerEntityRenderers();

        DoApi.LOGGER.error("R SPRITE");
        TerraformSignHelper.regsterSignSprite(new ResourceLocation("lol"));
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(DoApiEntityTypes.CHAIR, ChairRenderer::new);
    }

    public static void registerBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(DoApiBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
    }

}

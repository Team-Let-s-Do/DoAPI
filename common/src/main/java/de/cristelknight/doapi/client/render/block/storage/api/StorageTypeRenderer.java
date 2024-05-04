package de.cristelknight.doapi.client.render.block.storage.api;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface StorageTypeRenderer {
    void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks);
}

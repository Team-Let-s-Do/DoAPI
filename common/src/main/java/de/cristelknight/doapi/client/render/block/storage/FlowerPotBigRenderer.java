package de.cristelknight.doapi.client.render.block.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import static de.cristelknight.doapi.client.ClientUtil.renderBlock;

public class FlowerPotBigRenderer implements StorageTypeRenderer {

    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        ItemStack itemStack = itemStacks.get(0);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            BlockState state = blockItem.getBlock().defaultBlockState();
            matrices.translate(0f, 0.4f, 0f);
            renderBlock(state, matrices, vertexConsumers, entity);
            state = blockItem.getBlock().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
            matrices.translate(0f, 1f, 0f);
            renderBlock(state, matrices, vertexConsumers, entity);
        }
    }
}
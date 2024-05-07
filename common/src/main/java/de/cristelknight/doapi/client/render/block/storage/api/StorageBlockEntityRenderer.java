package de.cristelknight.doapi.client.render.block.storage.api;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.cristelknight.doapi.common.block.StorageBlock;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public class StorageBlockEntityRenderer implements BlockEntityRenderer<StorageBlockEntity> {


    private static final HashMap<ResourceLocation, StorageTypeRenderer> STORAGE_TYPES = new HashMap<>();


    public static ResourceLocation registerStorageType(ResourceLocation name, StorageTypeRenderer renderer){
        STORAGE_TYPES.put(name, renderer);
        return name;
    }



    public static StorageTypeRenderer getRendererForId(ResourceLocation name){

        return STORAGE_TYPES.get(name);
    }

    public StorageBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(StorageBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }

        BlockState state = entity.getBlockState();
        if (state.getBlock() instanceof StorageBlock sB) {
            NonNullList<ItemStack> itemStacks = entity.getInventory();
            if (itemStacks.isEmpty()) {
                return;
            }

            matrices.pushPose();
            applyBlockAngle(matrices, state, 180);
            ResourceLocation type = sB.type();
            StorageTypeRenderer renderer = getRendererForId(type);
            if (renderer != null) {
                renderer.render(entity, matrices, vertexConsumers, itemStacks);
            }
            matrices.popPose();
        }
    }

    public static void applyBlockAngle(PoseStack matrices, BlockState state, float angleOffset) {
        float angle = state.getValue(StorageBlock.FACING).toYRot();
        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.YP.rotationDegrees(angleOffset - angle));
    }
}

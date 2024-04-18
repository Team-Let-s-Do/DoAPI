package de.cristelknight.doapi.common.block.entity;

import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SideBoardBlockEntity extends ChestBlockEntity {
    public SideBoardBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(DoApiBlockEntityTypes.SIDEBOARD_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }
}

package de.cristelknight.doapi.common.block;

import de.cristelknight.doapi.common.block.entity.SideBoardBlockEntity;
import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SideBoardBlock extends ChestBlock {

    public static final Map<Direction, VoxelShape> SHAPES = Util.make(new HashMap<>(), map -> {
        map.put(Direction.NORTH, Block.box(0,0,4, 16, 16, 16));
        map.put(Direction.SOUTH, Block.box(0,0,0, 16, 16, 12));
        map.put(Direction.WEST, Block.box(4,0,0, 16, 16, 16));
        map.put(Direction.EAST, Block.box(0,0,0, 12, 16, 16));

    });

    public SideBoardBlock(Properties settings) {
        super(settings, DoApiBlockEntityTypes.SIDEBOARD_BLOCK_ENTITY::get);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SideBoardBlockEntity(pos, state);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}

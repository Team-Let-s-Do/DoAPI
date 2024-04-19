package de.cristelknight.doapi.common.block;

import de.cristelknight.doapi.common.registry.DoApiStorageTypesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class FlowerPotBigBlock extends StorageBlock {

	private static Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0.4375, 0.21875, 0.90625, 0.625, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.4375, 0.21875, 0.21875, 0.625, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0, 0.21875, 0.78125, 0.4375, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.4375, 0.09375, 0.90625, 0.625, 0.21875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.4375, 0.78125, 0.90625, 0.625, 0.90625), BooleanOp.OR);
		return shape;
	};
	private static final VoxelShape SHAPE = voxelShapeSupplier.get();
	
	public FlowerPotBigBlock(Properties settings) {
		super(settings);
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public ResourceLocation type() {
		return DoApiStorageTypesRegistry.FLOWER_POT_BIG;
	}

	@Override
	public Direction[] unAllowedDirections() {
		return new Direction[0];
	}

	@Override
	public boolean canInsertStack(ItemStack stack) {
		return stack.is(ItemTags.TALL_FLOWERS);
	}

	@Override
	public int getSection(Float x, Float y) {
		return 1;
	}

	@Override
	public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
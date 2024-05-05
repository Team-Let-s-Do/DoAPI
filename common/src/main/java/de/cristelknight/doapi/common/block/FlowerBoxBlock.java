package de.cristelknight.doapi.common.block;

import de.cristelknight.doapi.common.registry.DoApiStorageTypeRegistry;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class FlowerBoxBlock extends StorageBlock {
	public FlowerBoxBlock(Properties settings) {
		super(settings);
	}

	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.5625, 1, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.5625, 0.0625, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.5625, 0.9375, 0.375, 0.625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.625, 0.9375, 0.3125, 0.9375), BooleanOp.OR);
		return shape;
	};

	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	@Override
	public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE.get(state.getValue(FACING));
	}

	@Override
	public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.isShiftKeyDown())
			return InteractionResult.PASS;

		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public ResourceLocation type() {
		return DoApiStorageTypeRegistry.FLOWER_BOX;
	}

	@Override
	public Direction[] unAllowedDirections() {
		return new Direction[]{Direction.DOWN};
	}

	@Override
	public boolean canInsertStack(ItemStack stack) {
		return stack.is(ItemTags.SMALL_FLOWERS);
	}

	@Override
	public int getSection(Float x, Float y) {
		if(x < 0.5) return 0;
		return 1;
	}
}


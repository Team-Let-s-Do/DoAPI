package de.cristelknight.doapi.terraform.boat.item;

import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.core.BlockPos;

import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class TerraformBoatDispenserBehavior extends DefaultDispenseItemBehavior {
	private static final DispenseItemBehavior FALLBACK_BEHAVIOR = new DefaultDispenseItemBehavior();
	private static final double OFFSET_MULTIPLIER = 1.125F;

	private final ResourceLocation boatTypeName;
	private final boolean chest;

	public TerraformBoatDispenserBehavior(ResourceLocation boatTypeName, boolean chest) {
		this.boatTypeName = boatTypeName;
		this.chest = chest;
	}

	@Override
	public @NotNull ItemStack execute(BlockSource pointer, ItemStack stack) {
		Direction facing = pointer.state().getValue(DispenserBlock.FACING);
		Level world = pointer.level();
		Vec3 centerPos = pointer.center();

		double horizontalOffsetMultiplier = (OFFSET_MULTIPLIER + EntityType.BOAT.getWidth()) / 2.0d;
		double x = centerPos.x() + facing.getStepX() * horizontalOffsetMultiplier;
		double y = centerPos.y() + facing.getStepY() * OFFSET_MULTIPLIER;
		double z = centerPos.z() + facing.getStepZ() * horizontalOffsetMultiplier;

		BlockPos pos = pointer.pos().relative(facing);

		if (world.getFluidState(pos).is(FluidTags.WATER)) {
			y += 1.0d;
		} else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.below()).is(FluidTags.WATER)) {
			return FALLBACK_BEHAVIOR.dispense(pointer, stack);
		}

		Boat boatEntity = DoApiExpectPlatform.createBoat(this.boatTypeName, world, x, y, z, this.chest);
		boatEntity.setYRot(facing.toYRot());
		world.addFreshEntity(boatEntity);

		stack.shrink(1);
		return stack;
	}
}

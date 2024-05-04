package de.cristelknight.doapi.terraform.boat.item;

import de.cristelknight.doapi.DoApiCommonEP;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;


public class TerraformBoatDispenserBehavior extends DefaultDispenseItemBehavior {
	private static final DispenseItemBehavior FALLBACK_BEHAVIOR = new DefaultDispenseItemBehavior();
	private static final float OFFSET_MULTIPLIER = 1.125F;

	private final ResourceLocation boatTypeName;
	private final boolean chest;

	public TerraformBoatDispenserBehavior(ResourceLocation boatTypeName, boolean chest) {
		this.boatTypeName = boatTypeName;
		this.chest = chest;
	}

	@Override
	public @NotNull ItemStack execute(BlockSource pointer, ItemStack stack) {
		Direction facing = pointer.getBlockState().getValue(DispenserBlock.FACING);

		double x = pointer.x() + facing.getStepX() * OFFSET_MULTIPLIER;
		double y = pointer.y() + facing.getStepY() * OFFSET_MULTIPLIER;
		double z = pointer.z() + facing.getStepZ() * OFFSET_MULTIPLIER;

		Level world = pointer.getLevel();
		BlockPos pos = pointer.getPos().relative(facing);

		if (world.getFluidState(pos).is(FluidTags.WATER)) {
			y += 1;
		} else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.below()).is(FluidTags.WATER)) {
			return FALLBACK_BEHAVIOR.dispense(pointer, stack);
		}

		Boat boatEntity = DoApiCommonEP.createBoat(this.boatTypeName, world, x, y, z, this.chest);

		boatEntity.setYRot(facing.toYRot());

		world.addFreshEntity(boatEntity);

		stack.shrink(1);
		return stack;
	}
}

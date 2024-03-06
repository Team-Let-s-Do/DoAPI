package de.cristelknight.doapi.forge.terraform.boat.impl.entity;

import de.cristelknight.doapi.forge.terraform.boat.impl.TerraformBoatInitializer;
import de.cristelknight.doapi.forge.terraform.boat.impl.TerraformBoatTrackedData;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class TerraformChestBoatEntity extends ChestBoat implements TerraformBoatHolder {
	private static final EntityDataAccessor<Optional<TerraformBoatType>> TERRAFORM_BOAT = SynchedEntityData.defineId(TerraformChestBoatEntity.class, TerraformBoatTrackedData.HANDLER);

	public TerraformChestBoatEntity(EntityType<? extends TerraformChestBoatEntity> type, Level world) {
		super(type, world);
	}

	public TerraformChestBoatEntity(Level world) {
		this(TerraformBoatInitializer.CHEST_BOAT.get(), world);
	}

	public TerraformChestBoatEntity(Level world, double x, double y, double z) {
		this(TerraformBoatInitializer.CHEST_BOAT.get(), world);

		this.setPos(x, y, z);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	public TerraformBoatType getTerraformBoat() {
		return this.entityData.get(TERRAFORM_BOAT).orElse(null);
	}

	@Override
	public void setTerraformBoat(TerraformBoatType boat) {
		this.entityData.set(TERRAFORM_BOAT, Optional.of(boat));
	}

	@Override
	protected Component getTypeName() {
		return EntityType.CHEST_BOAT.getDescription();
	}

	@Override
	public Item getDropItem() {
		return this.getTerraformBoat().getChestItem();
	}

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return this.hasValidTerraformBoat() && super.shouldRender(cameraX, cameraY, cameraZ);
	}

	@Override
	public void tick() {
		if (this.hasValidTerraformBoat()) {
			super.tick();
		} else {
			this.discard();
		}
	}

	@Override
	public void setVariant(Type type) {
	}

	@Override
	public Type getVariant() {
		return this.getImpersonatedBoatType();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TERRAFORM_BOAT, Optional.empty());
	}

	// Serialization
	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.readTerraformBoatFromNbt(nbt);

		if (!this.hasValidTerraformBoat()) {
			this.discard();
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		this.writeTerraformBoatToNbt(nbt);
	}
}

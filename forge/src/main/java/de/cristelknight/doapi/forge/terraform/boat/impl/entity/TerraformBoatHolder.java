package de.cristelknight.doapi.forge.terraform.boat.impl.entity;

import de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public interface TerraformBoatHolder {
	static final String BOAT_KEY = "TerraformBoat";

	TerraformBoatType getTerraformBoat();

	void setTerraformBoat(TerraformBoatType boat);

	default boolean hasValidTerraformBoat() {
		return this.getTerraformBoat() != null;
	}

	default void readTerraformBoatFromNbt(CompoundTag nbt) {
		ResourceLocation id = ResourceLocation.tryParse(nbt.getString(BOAT_KEY));
		if (id != null) {
			TerraformBoatType boat = TerraformBoatTypeRegistry.get(id);
			if (boat != null) {
				this.setTerraformBoat(boat);
			}
		}
	}

	default void writeTerraformBoatToNbt(CompoundTag nbt) {
		ResourceLocation boatId = TerraformBoatTypeRegistry.getId(this.getTerraformBoat());
		if (boatId != null) {
			nbt.putString(BOAT_KEY, boatId.toString());
		}
	}

	default Boat.Type getImpersonatedBoatType() {
		return this.getTerraformBoat().isRaft() ? Boat.Type.BAMBOO : Boat.Type.OAK;
	}
}

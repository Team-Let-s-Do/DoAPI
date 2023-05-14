package de.cristelknight.doapi.forge.terraform.boat.impl;

import de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
public final class TerraformBoatTrackedData {
	private TerraformBoatTrackedData() {
		return;
	}

	public static final EntityDataSerializer<TerraformBoatType> HANDLER = new EntityDataSerializer<>() {
		public void write(FriendlyByteBuf buf, TerraformBoatType boat) {
			buf.writeResourceLocation(TerraformBoatTypeRegistry.getId(boat));
		}

		public TerraformBoatType read(FriendlyByteBuf buf) {
			return TerraformBoatTypeRegistry.get(buf.readResourceLocation());
		}

		public TerraformBoatType copy(TerraformBoatType boat) {
			return boat;
		}
	};

	public static void register() {
		EntityDataSerializers.registerSerializer(HANDLER);
	}
}

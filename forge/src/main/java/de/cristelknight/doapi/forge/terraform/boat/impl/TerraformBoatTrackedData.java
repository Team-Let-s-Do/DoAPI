package de.cristelknight.doapi.forge.terraform.boat.impl;

import de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

import java.util.Optional;

public final class TerraformBoatTrackedData {
	private TerraformBoatTrackedData() {
	}

	public static final EntityDataSerializer<Optional<TerraformBoatType>> HANDLER = EntityDataSerializer.optional(TerraformBoatTrackedData::write, TerraformBoatTrackedData::read);

	private static void write(FriendlyByteBuf buf, TerraformBoatType boat) {
		buf.writeResourceLocation(TerraformBoatTypeRegistry.getId(boat));
	}

	private static TerraformBoatType read(FriendlyByteBuf buf) {
		return TerraformBoatTypeRegistry.get(buf.readResourceLocation());
	}

	public static void register() {
		EntityDataSerializers.registerSerializer(HANDLER);
	}
}

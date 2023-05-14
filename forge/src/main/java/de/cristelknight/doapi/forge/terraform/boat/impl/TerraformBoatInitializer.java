package de.cristelknight.doapi.forge.terraform.boat.impl;

import de.cristelknight.doapi.forge.terraform.TerraformApiForge;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatEntity;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformChestBoatEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public final class TerraformBoatInitializer {

	private static final Registrar<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(TerraformApiForge.TERRAFORM_MOD_ID, Registries.ENTITY_TYPE).getRegistrar();

	// Hack that prevents the following crash during client startup:
	// Caused by: java.lang.NoClassDefFoundError: Could not initialize class com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry

	private static final ResourceLocation BOAT_ID = new ResourceLocation(TerraformApiForge.TERRAFORM_MOD_ID, "boat");
	public static final RegistrySupplier<EntityType<TerraformBoatEntity>> BOAT =  create("boat", () -> EntityType.Builder.<TerraformBoatEntity>of(TerraformBoatEntity::new, MobCategory.MISC)
			.sized(1.375f, 0.5625f)
		.build(BOAT_ID.toString()));

	private static final ResourceLocation CHEST_BOAT_ID = new ResourceLocation(TerraformApiForge.TERRAFORM_MOD_ID, "chest_boat");
	public static final RegistrySupplier<EntityType<TerraformChestBoatEntity>> CHEST_BOAT = create("chest_boat",
			() -> EntityType.Builder.<TerraformChestBoatEntity>of(TerraformChestBoatEntity::new, MobCategory.MISC)
		.sized(1.375f, 0.5625f)
		.build(CHEST_BOAT_ID.toString()));

	public static void init() {
		TerraformBoatTrackedData.register();
	}

	public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
		return ENTITY_TYPES.register(new ResourceLocation(TerraformApiForge.TERRAFORM_MOD_ID, path), type);
	}
}

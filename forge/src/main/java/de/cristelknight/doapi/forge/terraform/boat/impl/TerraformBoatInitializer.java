package de.cristelknight.doapi.forge.terraform.boat.impl;


import de.cristelknight.doapi.forge.terraform.TerraformApiForge;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatEntity;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformChestBoatEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public final class TerraformBoatInitializer {

	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(TerraformApiForge.TERRAFORM_MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

	private static final ResourceLocation BOAT_ID = new ResourceLocation(TerraformApiForge.TERRAFORM_MOD_ID,"boat");

	public static final RegistrySupplier<EntityType<TerraformBoatEntity>> BOAT = create(BOAT_ID, () -> EntityType.Builder.<TerraformBoatEntity>of(TerraformBoatEntity::new, MobCategory.MISC)
		.sized(1.375f, 0.5625f)
		.build(BOAT_ID.toString()));

	private static final ResourceLocation CHEST_BOAT_ID = new ResourceLocation(TerraformApiForge.TERRAFORM_MOD_ID,"chest_boat");
	public static final RegistrySupplier<EntityType<TerraformChestBoatEntity>> CHEST_BOAT = create(CHEST_BOAT_ID, () -> EntityType.Builder.<TerraformChestBoatEntity>of(TerraformChestBoatEntity::new, MobCategory.MISC)
		.sized(1.375f, 0.5625f)
		.build(CHEST_BOAT_ID.toString()));


	public static void init() {
		TerraformBoatTrackedData.register();
		ENTITY_TYPES.register();
	}

	public static <T extends EntityType<?>> RegistrySupplier<T> create(final ResourceLocation path, final Supplier<T> type) {
		return ENTITY_TYPES.register(path, type);
	}
}

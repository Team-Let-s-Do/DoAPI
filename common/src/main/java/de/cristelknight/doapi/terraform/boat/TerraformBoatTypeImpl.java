package de.cristelknight.doapi.terraform.boat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final RegistrySupplier<Item> item;
	private final RegistrySupplier<Item> chestItem;

	public TerraformBoatTypeImpl(RegistrySupplier<Item> item, RegistrySupplier<Item> chestItem) {
		this.item = item;
		this.chestItem = chestItem;
	}

	@Override
	public Item getItem() {
		return this.item.orElse(Items.OAK_BOAT);
	}

	@Override
	public Item getChestItem() {
		return this.chestItem.orElse(Items.OAK_CHEST_BOAT);
	}
}

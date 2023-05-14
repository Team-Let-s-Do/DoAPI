package de.cristelknight.doapi.terraform.boat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final boolean raft;
	private final RegistrySupplier<Item> item;
	private final RegistrySupplier<Item> chestItem;

	public TerraformBoatTypeImpl(boolean raft, RegistrySupplier<Item> item, RegistrySupplier<Item> chestItem) {
		this.raft = raft;
		this.item = item;
		this.chestItem = chestItem;
	}

	@Override
	public boolean isRaft() {
		return this.raft;
	}

	@Override
	public Item getItem() {
		return this.item.orElse(Items.OAK_BOAT);
	}

	@Override
	public Item getChestItem() {
		return this.chestItem.orElse(Items.OAK_CHEST_BOAT);
	}

	@Override
	public Item getPlanks() {
		return Items.OAK_PLANKS;
	}
}

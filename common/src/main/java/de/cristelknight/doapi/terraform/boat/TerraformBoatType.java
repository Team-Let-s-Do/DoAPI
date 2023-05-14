package de.cristelknight.doapi.terraform.boat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

public interface TerraformBoatType {
	boolean isRaft();
	Item getItem();
	Item getChestItem();
	Item getPlanks();

	public static class Builder {
		private boolean raft;
		private RegistrySupplier<Item> item;
		private RegistrySupplier<Item> chestItem;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.raft, this.item, this.chestItem);
		}
		public Builder raft() {
			this.raft = true;
			return this;
		}

		public Builder item(RegistrySupplier<Item> item) {
			this.item = item;
			return this;
		}

		public Builder chestItem(RegistrySupplier<Item> chestItem) {
			this.chestItem = chestItem;
			return this;
		}

	}
}

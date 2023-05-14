package de.cristelknight.doapi.terraform.boat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


public interface TerraformBoatType {

	Item getItem();

	Item getChestItem();
	default Item getPlanks() {
		return Items.OAK_PLANKS;
	}

	static Builder builder(){
		return new Builder();
	}

	public static class Builder {
		private RegistrySupplier<Item> item;
		private RegistrySupplier<Item> chestItem;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.item, this.chestItem);
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

package de.cristelknight.doapi.fabric.terraform;

import com.terraformersmc.terraform.boat.impl.TerraformBoatTypeImpl;
import net.minecraft.world.item.Item;

public class DoApiBoatTypeHolder extends TerraformBoatTypeImpl {
    public DoApiBoatTypeHolder(Item item, Item chestItem, Item planks) {
        super(item, chestItem, planks);
    }
}
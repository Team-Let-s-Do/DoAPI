package de.cristelknight.doapi.fabric.terraform;

import com.terraformersmc.terraform.boat.impl.TerraformBoatTypeImpl;
import net.minecraft.world.item.Item;

public class DoApiBoatTypeHolder extends TerraformBoatTypeImpl {
    public DoApiBoatTypeHolder(boolean raft, Item item, Item chestItem, Item planks) {
        super(raft, item, chestItem, planks);
    }
}

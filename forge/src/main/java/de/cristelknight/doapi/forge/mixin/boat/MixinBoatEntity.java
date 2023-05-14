package de.cristelknight.doapi.forge.mixin.boat;

import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatHolder;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Boat.class)
public class MixinBoatEntity {
	@ModifyArg(method = "checkFallDamage", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 0))
	private ItemLike replaceTerraformPlanksDropItem(ItemLike original) {
		if (this instanceof TerraformBoatHolder) {
			TerraformBoatType boat = ((TerraformBoatHolder) this).getTerraformBoat();
			return boat.getPlanks();
		}
		return original;
	}
}

package de.cristelknight.doapi.forge.mixin.boat;

import de.cristelknight.doapi.forge.terraform.boat.impl.TerraformBoatInitializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Shulker.class)
public class MixinShulkerEntity {
	@Redirect(method = "getMyRidingOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getType()Lnet/minecraft/world/entity/EntityType;"))
	private EntityType<?> fixTerraformBoatHeightOffset(Entity entity) {
		if (entity.getType() == TerraformBoatInitializer.BOAT.get()) {
			return EntityType.BOAT;
		}
		return entity.getType();
	}
}

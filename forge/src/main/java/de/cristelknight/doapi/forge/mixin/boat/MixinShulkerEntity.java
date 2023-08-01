package de.cristelknight.doapi.forge.mixin.boat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.cristelknight.doapi.forge.terraform.boat.impl.TerraformBoatInitializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Shulker.class)
public class MixinShulkerEntity {
	@WrapOperation(method = "getMyRidingOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getType()Lnet/minecraft/world/entity/EntityType;"))
	@SuppressWarnings("unused")
	private EntityType<?> fixTerraformBoatHeightOffset(Entity instance, Operation<EntityType<?>> original) {
		if (instance.getType() == TerraformBoatInitializer.BOAT.get()) {
			return EntityType.BOAT;
		}
		return original.call(instance);
	}
}

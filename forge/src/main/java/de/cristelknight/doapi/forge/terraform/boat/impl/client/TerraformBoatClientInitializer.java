package de.cristelknight.doapi.forge.terraform.boat.impl.client;


import de.cristelknight.doapi.forge.terraform.boat.impl.TerraformBoatInitializer;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class TerraformBoatClientInitializer {

	public static void init(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(TerraformBoatInitializer.BOAT.get(), context -> new TerraformBoatEntityRenderer(context, false));
		event.registerEntityRenderer(TerraformBoatInitializer.CHEST_BOAT.get(), context -> new TerraformBoatEntityRenderer(context, true));
	}
}

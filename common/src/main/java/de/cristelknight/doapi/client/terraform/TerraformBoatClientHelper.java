package de.cristelknight.doapi.client.terraform;

import de.cristelknight.doapi.DoApiExpectPlatform;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public final class TerraformBoatClientHelper {
	private TerraformBoatClientHelper() {
		return;
	}

	private static ResourceLocation getLayerId(ResourceLocation boatId, boolean chest) {
		String prefix = chest ? "chest_boat/" : "boat/";
		return new ResourceLocation(boatId.getNamespace(), prefix + boatId.getPath());
	}

	public static ModelLayerLocation getLayer(ResourceLocation boatId, boolean chest) {
		return new ModelLayerLocation(getLayerId(boatId, chest), "main");
	}

	private static void registerModelLayer(ResourceLocation boatId, boolean chest) {
		EntityModelLayerRegistry.register(getLayer(boatId, chest), () -> BoatModel.createBodyModel(chest));
	}

	public static void registerModelLayers(ResourceLocation boatId) {
		registerModelLayer(boatId, false);
		registerModelLayer(boatId, true);
	}

	public static void registerAllModelLayers(){
		for(ResourceLocation location : DoApiExpectPlatform.getAllDoApiBoatTypeNames()){
			registerModelLayers(location);
		}
	}

	public static void preClientInit() {
		TerraformBoatClientHelper.registerAllModelLayers();
	}

}

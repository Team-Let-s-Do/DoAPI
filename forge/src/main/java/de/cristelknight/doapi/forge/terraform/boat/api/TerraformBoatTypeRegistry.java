package de.cristelknight.doapi.forge.terraform.boat.api;

import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TerraformBoatTypeRegistry {

	private static final Map<ResourceLocation, TerraformBoatType> INSTANCE = new HashMap<>();

	public static void register(ResourceLocation location, TerraformBoatType type){
		INSTANCE.put(location, type);
	}

	public static TerraformBoatType get(ResourceLocation location){
		return INSTANCE.get(location);
	}

	public static Set<Map.Entry<ResourceLocation, TerraformBoatType>> entrySet(){
		return INSTANCE.entrySet();
	}

	public static Set<ResourceLocation> getIds(){
		return INSTANCE.keySet();
	}

	public static ResourceLocation getId(TerraformBoatType type){
		for(ResourceLocation location : getIds()){
			if(get(location).equals(type)) return location;
		}
		throw new NullPointerException("Couldn't find BoatType");
	}


}

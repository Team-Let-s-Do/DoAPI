package de.cristelknight.doapi.forge.terraform.sign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

public class SpriteIdentifierRegistry {
	public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();
	private final List<Material> identifiers;

	private final List<ResourceLocation> locations;

	private SpriteIdentifierRegistry() {
		identifiers = new ArrayList<>();
		locations = new ArrayList<>();
	}

	public void addIdentifier(ResourceLocation sprite) {
		this.locations.add(sprite);
	}

	public Collection<Material> getIdentifiers() {
		if(!locations.isEmpty()){ // stupid forge fix
			for(ResourceLocation location : locations){
				identifiers.add(new Material(Sheets.SIGN_SHEET, location));
			}
			locations.clear();
		}
		return Collections.unmodifiableList(identifiers);
	}
}

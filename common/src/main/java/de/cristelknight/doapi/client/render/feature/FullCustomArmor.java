package de.cristelknight.doapi.client.render.feature;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Set;

public class FullCustomArmor {
    public final Set<Item> set;
    public final ResourceLocation texture;
    public FullCustomArmor(/*Item head, */Item chest, Item legs, Item boots, ResourceLocation texture){
        this.set = Set.of(/*head, */chest, legs, boots);
        this.texture = texture;
    }

}

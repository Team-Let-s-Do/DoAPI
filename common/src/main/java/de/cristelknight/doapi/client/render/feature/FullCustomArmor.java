package de.cristelknight.doapi.client.render.feature;

import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class FullCustomArmor {
    public Set<ICustomArmor> set;
    public ResourceLocation texture;
    public FullCustomArmor(/*Item head, */ICustomArmor chest, ICustomArmor legs, ICustomArmor boots, ResourceLocation texture){
        this.set = Set.of(/*head, */chest, legs, boots);
        this.texture = texture;
    }
}

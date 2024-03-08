package de.cristelknight.doapi.client.render.feature;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

@Deprecated
public class FullCustomArmor<T extends LivingEntity> extends CustomArmorSet<T> {
    public FullCustomArmor(/*Item head, */Item chest, Item legs, Item boots, ResourceLocation texture) {
        super(null, chest, legs, boots);
        this.setTexture(texture);
    }
}

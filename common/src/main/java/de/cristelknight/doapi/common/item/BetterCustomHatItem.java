package de.cristelknight.doapi.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class BetterCustomHatItem extends CustomHatItem {
    public ResourceLocation texture;
    public float offset;

    public BetterCustomHatItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties settings, ResourceLocation texture, float offset) {
        super(material, type, settings);
        this.texture = texture;
        this.offset = offset;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public Float getOffset() {
        return offset;
    }

}

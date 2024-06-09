package de.cristelknight.doapi.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

@Deprecated
public class BetterCustomHatItem extends CustomHatItem {
    public final ResourceLocation texture;
    public final float offset;

    public BetterCustomHatItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties settings, ResourceLocation texture, float offset) {
        super(material, type, settings);
        this.texture = texture;
        this.offset = offset;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }

    @Override
    public Float doapi$getYOffset() {
        return this.offset;
    }

}

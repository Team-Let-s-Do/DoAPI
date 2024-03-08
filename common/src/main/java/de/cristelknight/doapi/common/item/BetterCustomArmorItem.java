package de.cristelknight.doapi.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;


public class BetterCustomArmorItem extends ArmorItem implements ICustomArmor {
    public ResourceLocation texture;
    public float offset;

    public BetterCustomArmorItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties settings, float offset) {
        super(material, type, settings);
        this.offset = offset;
    }

    @Deprecated
    public BetterCustomArmorItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties settings, ResourceLocation texture, float offset) {
        super(material, type, settings);
        this.offset = offset;
    }

    @Override
    public Float getYOffset() {
        return this.offset;
    }

}

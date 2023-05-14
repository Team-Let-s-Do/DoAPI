package de.cristelknight.doapi.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class BetterCustomArmorModelItem extends CustomArmorModelItem {

    public ResourceLocation texture;

    public float offset;
    public BetterCustomArmorModelItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties settings, ResourceLocation texture, float offset) {
        super(material, slot, settings);
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

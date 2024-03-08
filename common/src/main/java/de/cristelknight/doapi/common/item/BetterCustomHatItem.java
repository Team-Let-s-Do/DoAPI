package de.cristelknight.doapi.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class BetterCustomHatItem extends ArmorItem implements ICustomArmor {
    public float offset;

    public BetterCustomHatItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties settings, float offset) {
        super(material, slot, settings);
        this.offset = offset;
    }

    public BetterCustomHatItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties settings, ResourceLocation texture, float offset) {
        super(material, slot, settings);
        this.offset = offset;
    }
    @Override
    public Float getYOffset() {
        return this.offset;
    }

}

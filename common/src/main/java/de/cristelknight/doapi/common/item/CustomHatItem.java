package de.cristelknight.doapi.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public abstract class CustomHatItem extends ArmorItem {
    public CustomHatItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties settings) {
        super(material, type, settings);
    }

    public abstract ResourceLocation getTexture();

    public abstract Float getOffset();


}
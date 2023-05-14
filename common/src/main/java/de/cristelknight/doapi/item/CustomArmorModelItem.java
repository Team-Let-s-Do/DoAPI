package de.cristelknight.doapi.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public abstract class CustomArmorModelItem extends ArmorItem {
    public CustomArmorModelItem(ArmorMaterial material, ArmorItem.Type slot, Item.Properties settings) {
        super(material, slot, settings);
    }

    public abstract ResourceLocation getTexture();

    public abstract Float getOffset();


}
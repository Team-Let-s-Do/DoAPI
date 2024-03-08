package de.cristelknight.doapi.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

@Deprecated
public abstract class CustomHatItem extends ArmorItem implements ICustomHat {
    protected CustomHatItem(ArmorMaterial armorMaterial, EquipmentSlot slot, Properties properties) {
        super(armorMaterial, slot, properties);
    }

}
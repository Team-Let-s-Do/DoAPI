package de.cristelknight.doapi.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

@Deprecated
public abstract class CustomArmorItem extends ArmorItem implements ICustomArmor {
    protected CustomArmorItem(ArmorMaterial armorMaterial, EquipmentSlot slot, Properties properties) {
        super(armorMaterial, slot, properties);
    }
}
package de.cristelknight.doapi.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

@Deprecated
public abstract class CustomArmorItem extends ArmorItem implements ICustomArmor {
    public CustomArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }
}
package de.cristelknight.doapi.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

@Deprecated
public abstract class CustomHatItem extends ArmorItem implements ICustomHat {
    public CustomHatItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

}
package de.cristelknight.doapi.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

/** Legacy class, extend {@link ArmorItem} instead. **/
@Deprecated
public abstract class CustomArmorItem extends ArmorItem {
    public CustomArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }
}
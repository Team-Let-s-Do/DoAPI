package de.cristelknight.doapi.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

/** Legacy class, implement {@link ICustomHat} or use {@link BetterCustomHatItem} instead. **/
@Deprecated
public abstract class CustomHatItem extends ArmorItem implements ICustomHat {
    public CustomHatItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Float getOffset() {
        return doapi$getYOffset();
    }
}
package de.cristelknight.doapi.common.item;

import org.jetbrains.annotations.ApiStatus;

/** Injected into ArmorItem, do not implement directly **/
@ApiStatus.Internal
public interface ICustomArmor {
    default Float doapi$getYOffset() {
        return 0.0F;
    }
}

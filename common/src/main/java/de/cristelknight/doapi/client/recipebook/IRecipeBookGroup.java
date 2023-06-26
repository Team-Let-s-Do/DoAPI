package de.cristelknight.doapi.client.recipebook;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public interface IRecipeBookGroup {
    /**
     * Check if the given recipe fits in the current category
     * @param recipe the recipe to check
     */
    boolean fitRecipe(Recipe<? extends Container> recipe, RegistryAccess registryManager);
    List<ItemStack> getIcons();
}

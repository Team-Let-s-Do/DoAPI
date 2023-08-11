package de.cristelknight.doapi.fabric.common.registry;

import de.cristelknight.doapi.fabric.common.recipe.SimpleConditionalRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DoApiRecipes {

    public static final RecipeSerializer<Recipe<?>> CONDITIONAL_RECIPE_SERIALIZER = create(new ResourceLocation("forge", "conditional"), new SimpleConditionalRecipe.Serializer<>());

    private static <T extends Recipe<?>> RecipeSerializer<T> create(ResourceLocation location, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, location, serializer);
    }

    public static void loadClass(){}
}

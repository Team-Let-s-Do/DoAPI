package de.cristelknight.doapi.fabric.common.registry;

import de.cristelknight.doapi.fabric.common.recipe.SimpleConditionalRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class DoApiRecipes {

    public static final RecipeType<SimpleConditionalRecipe> CONDITIONAL_RECIPE_TYPE = create(new ResourceLocation("forge", "conditional"));
    public static final RecipeSerializer<SimpleConditionalRecipe> CONDITIONAL_RECIPE_SERIALIZER = create(new ResourceLocation("forge", "conditional"), new SimpleConditionalRecipe.Serializer<>());

    private static <T extends Recipe<?>> RecipeSerializer<T> create(ResourceLocation location, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, location, serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> create(ResourceLocation location) {
        RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return location.getPath();
            }
        };
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, location, type);
    }
}

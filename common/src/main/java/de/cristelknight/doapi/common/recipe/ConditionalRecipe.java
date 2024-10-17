package de.cristelknight.doapi.common.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.*;
import de.cristelknight.doapi.DoApiEP;
import de.cristelknight.doapi.common.recipe.codec.ConditionalCodecs;
import de.cristelknight.doapi.common.recipe.codec.Conditionals;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class ConditionalRecipe {
    public static final String DEFAULT_FIELD = "doapi:conditional";

    private static final MapCodec<Recipe<?>> CODEC = new MapCodec<Recipe<?>>() {
        @Override
        public <T> RecordBuilder<T> encode(Recipe<?> input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
            if(!(input instanceof Wrapper wrapper))
                throw new IllegalArgumentException("ConditionalRecipe.CODEC can only encode ConditionalRecipe.Wrapper instances");

            prefix.add(DEFAULT_FIELD, ConditionalCodecs.CONDITIONALS_CODEC.encodeStart(ops, wrapper.conditions));

            prefix.add(DEFAULT_FIELD, Recipe.CODEC.encodeStart(ops, wrapper.recipe));

            return prefix;
        }

        @Override
        public <T> DataResult<Recipe<?>> decode(DynamicOps<T> ops, MapLike<T> input) {
            var recipeElement = input.get("recipe");
            if (recipeElement == null) {
                return DataResult.error(() -> "Missing `recipe` entry");
            }

            var recipe = Recipe.CODEC.parse(ops, recipeElement);
            if (recipe.error().isPresent()) {
                return DataResult.error(() -> "Failed to parse `recipe` entry: " + recipe.error().get().message());
            }

            return recipe;
        }


        @Override
        public <T> Stream<T> keys(DynamicOps<T> ops) {
            return Stream.of(ops.createString(DEFAULT_FIELD), ops.createString("recipe"));
        }

        private <T> DataResult<Recipe<?>> accept(DynamicOps<T> ops, Holder<Integer> count, T entry) {
            count.value = count.value + 1;
            var map = ops.getMap(entry).result().orElse(null);
            if (map == null) {
                return DataResult.error(() -> "Entry " + count.value + " was not MapLike " + entry.getClass());
            }

            if (map.get(DEFAULT_FIELD) != null) {
                var parsed = ConditionalCodecs.CONDITIONALS_CODEC.parse(ops, (T)map.get(DEFAULT_FIELD));
                if (parsed.result().isPresent()) {
                } else {
                    return DataResult.error(() -> "Failed to parse condition " + count.value);
                }
            }

            var recipe = map.get("recipe");
            if (recipe == null) {
                return DataResult.error(() -> "Missing `recipe` entry " + count.value);
            }

            return Recipe.CODEC.parse(ops, (T)recipe);
        }
    }.stable();



    private static class Wrapper implements Recipe<CraftingInput> {
        @Override public boolean matches(CraftingInput inv, Level level) { return false; }
        @Override public ItemStack assemble(CraftingInput inv, HolderLookup.Provider reg) { return null; }
        @Override public boolean canCraftInDimensions(int width, int height) { return false; }
        @Override public ItemStack getResultItem(HolderLookup.Provider reg) { return null; }
        @Override public RecipeSerializer<?> getSerializer() { return ConditionalRecipe.SERIALIZER; }
        @Override public RecipeType<?> getType() { throw new UnsupportedOperationException(); }

        private final Conditionals conditions;
        private final Recipe<?> recipe;

        private Wrapper(Conditionals main, Recipe<?> recipes) {
            this.conditions = main;
            this.recipe = recipes;
        }
    }

    public static final RecipeSerializer<Recipe<?>> SERIALIZER = new RecipeSerializer<>() {
        @Override
        public @NotNull MapCodec<Recipe<?>> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, Recipe<?>> streamCodec() {
            throw new UnsupportedOperationException("ConditionaRecipe.SERIALIZER does not support encoding to network");
        }
    };


    private static final class Holder<T> {
        private T value;
    }

    public static boolean checkCondition(JsonObject c){
        String type = GsonHelper.getAsString(c, "type");

        if(type.equals("neoforge:mod_loaded")){
            String modId = c.get("modid").getAsString();
            return DoApiEP.isModLoaded(modId);
        }
        return false;
    }
}

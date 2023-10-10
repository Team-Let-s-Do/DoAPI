package de.cristelknight.doapi.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleConditionalRecipe {


    private static final Codec<Recipe<?>> CODEC = new Codec<Recipe<?>>() {
        @Override
        public <T> DataResult<T> encode(Recipe<?> input, DynamicOps<T> ops, T prefix) {
            throw new UnsupportedOperationException("DoAPI conditional recipe codec does not support encoding");
        }

        @Override
        public <T> DataResult<Pair<Recipe<?>, T>> decode(DynamicOps<T> ops, T input) {
            JsonElement json = new Dynamic<>(ops, input).convert(JsonOps.INSTANCE).getValue();
            try {
               return DoApiExpectPlatform.decode(ops, json);
            } catch (JsonSyntaxException e) {
                return DataResult.error(e::getMessage);
            }
        }
    }.stable();
    public static final RecipeSerializer<Recipe<?>> SERIALZIER = new RecipeSerializer<>() {
        @Override
        public Codec<Recipe<?>> codec() {
            return CODEC;
        }

        @Override
        public Recipe<?> fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            throw new UnsupportedOperationException("DoAPI conditional recipe serializer does not support decoding from network");
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Recipe<?> recipe) {
            throw new UnsupportedOperationException("DoAPI conditional recipe serializer does not support support encoding to network");
        }
    };

    public static <T> DataResult<Pair<Recipe<?>, T>> checkResult(DataResult<Recipe<?>> parsed, DynamicOps<T> ops){
        if (parsed.error().isPresent())
            return DataResult.error(parsed.error().get()::message);
        else if (!parsed.result().isPresent())
            return DataResult.error(() -> "Recipe did not parse a valid return");
        else
            return DataResult.success(Pair.of(parsed.result().get(), ops.empty()));
    }

    public static boolean checkCondition(JsonObject c){
        String type = GsonHelper.getAsString(c, "type");

        if(type.equals("forge:mod_loaded")){
            String modId = c.get("modid").getAsString();
            return DoApiExpectPlatform.isModLoaded(modId);
        }
        return false;
    }
}

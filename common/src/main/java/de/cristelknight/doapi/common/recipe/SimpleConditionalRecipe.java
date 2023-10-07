package de.cristelknight.doapi.common.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleConditionalRecipe {

    public static class Serializer<T extends Recipe<?>> implements RecipeSerializer<T> {

        // We return a nested one, so we can't know what type it is.
        public T fromJson(ResourceLocation recipeId, JsonObject json) {
            DoApi.LOGGER.debug("Starting to load conditional recipe named: " + recipeId);

            return DoApiExpectPlatform.fromJson(recipeId, json);
        }

        //Should never get here as we return one of the recipes we wrap.
        @Override
        public T fromNetwork(FriendlyByteBuf buf) { return null; }

        @Override
        public Codec<T> codec() {
            return null;
        }

        @Override public void toNetwork(FriendlyByteBuf buffer, T recipe) {}
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

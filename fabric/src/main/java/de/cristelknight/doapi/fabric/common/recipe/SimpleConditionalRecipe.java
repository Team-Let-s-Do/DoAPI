package de.cristelknight.doapi.fabric.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.fabric.DoApiExpectPlatformImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleConditionalRecipe {

    public static class Serializer<T extends Recipe<?>> implements RecipeSerializer<T> {

        @SuppressWarnings("unchecked") // We return a nested one, so we can't know what type it is.
        @Override
        public T fromJson(ResourceLocation recipeId, JsonObject json) {
            if(!recipeId.getNamespace().equals("conditional")) throw new UnsupportedOperationException("[DoAPI] All fabric conditional recipes should be under the conditional namespace! " + recipeId + "isn't!");
            //if(checkCondition(c, type)) Not necessary, because of our mixin
            DoApi.LOGGER.debug("Loading conditional recipe named: " + recipeId);

            return (T) RecipeManager.fromJson(recipeId, GsonHelper.getAsJsonObject(getRecipe(json), "recipe"));
        }

        //Should never get here as we return one of the recipes we wrap.
        @Override public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) { return null; }
        @Override public void toNetwork(FriendlyByteBuf buffer, T recipe) {}
    }

    public static JsonObject getRecipe(JsonObject json){
        JsonArray array = GsonHelper.getAsJsonArray(json, "recipes");
        return array.get(0).getAsJsonObject();
    }

    public static boolean checkCondition(JsonObject c){
        String type = GsonHelper.getAsString(c, "type");

        if(type.equals("forge:mod_loaded")){
            String modId = c.get("modid").getAsString();
            return DoApiExpectPlatformImpl.isModLoaded(modId);
        }
        return false;
    }
}

package de.cristelknight.doapi.fabric.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.fabric.common.recipe.SimpleConditionalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "HEAD"))
    public void checkConditions(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        Iterator<Map.Entry<ResourceLocation, JsonElement>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, JsonElement> entry = iterator.next();
            ResourceLocation r = entry.getKey();
            if(!r.getNamespace().equals("conditional")) continue;

            JsonObject json = map.get(r).getAsJsonObject();
            if(!GsonHelper.getAsString(json, "type").equals("forge:conditional")) continue;

            JsonArray conditions = GsonHelper.getAsJsonArray(SimpleConditionalRecipe.getRecipe(json), "conditions");

            for(JsonElement e : conditions){
                if(SimpleConditionalRecipe.checkCondition(e.getAsJsonObject())) continue;
                DoApi.LOGGER.debug("Condition for recipe: {} is not met!", r);
                iterator.remove();
                break;
            }
        }
    }

}
package de.cristelknight.doapi.forge.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.common.recipe.SimpleConditionalRecipe;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @WrapOperation(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeHooks;readAndTestCondition(Lnet/minecraftforge/common/crafting/conditions/ICondition$IContext;Lcom/google/gson/JsonObject;)Z"))
    public boolean checkConditions(ICondition.IContext context, JsonObject json, Operation<Boolean> original) {
        if(GsonHelper.getAsString(json, "type").equals("doapi:conditional")){
            JsonArray conditions = GsonHelper.getAsJsonArray(json, "conditions");
            for(JsonElement e : conditions){
                if(SimpleConditionalRecipe.checkCondition(e.getAsJsonObject())) continue;
                DoApi.LOGGER.debug("Condition for DoAPI Forge conditional recipe is not met!");
                return false;
            }
        }
        return original.call(context, json);
    }

}
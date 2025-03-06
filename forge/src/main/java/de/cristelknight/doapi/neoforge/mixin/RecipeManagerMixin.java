package de.cristelknight.doapi.neoforge.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.common.recipe.ConditionalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Unique
    private static boolean doAPI$skip = false;

    @WrapOperation(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresentOrElse(Ljava/util/function/Consumer;Ljava/lang/Runnable;)V")
    )
    public <T extends WithConditions<Recipe<?>>> void doAPI$skipRecipe(Optional<WithConditions<Recipe<?>>> instance, Consumer<? super T> action, Runnable emptyAction, Operation<Void> original) {
        if(doAPI$skip){
            emptyAction.run();
            doAPI$skip = false;
            return;
        }
        original.call(instance, action, emptyAction);
    }

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresentOrElse(Ljava/util/function/Consumer;Ljava/lang/Runnable;)V")
    )
    public void doAPI$checkConditions(Map<ResourceLocation, JsonElement> map, ResourceManager arg, ProfilerFiller arg2, CallbackInfo ci, @Local Map.Entry<ResourceLocation, JsonElement> entry) {
        JsonObject json = entry.getValue().getAsJsonObject();
        if(!GsonHelper.getAsString(json, "type").equals(ConditionalRecipe.DEFAULT_FIELD)) return;

        JsonArray conditions = GsonHelper.getAsJsonArray(json, "conditions");
        for(JsonElement e : conditions){
            if(ConditionalRecipe.checkCondition(e.getAsJsonObject())) continue;
            DoApi.LOGGER.debug("Condition for DoAPI Forge conditional recipe is not met!");
            doAPI$skip = true;
            return;
        }
    }
}
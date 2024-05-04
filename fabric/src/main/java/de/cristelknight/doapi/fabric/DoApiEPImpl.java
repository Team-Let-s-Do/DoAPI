package de.cristelknight.doapi.fabric;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.nio.file.Path;
import java.util.List;

public class DoApiEPImpl {
    @SuppressWarnings("unchecked")
    public static <T extends Recipe<?>> T fromJson(ResourceLocation recipeId, JsonObject json) {
        if(!recipeId.getNamespace().equals("conditional")) throw new UnsupportedOperationException("[DoAPI] All vinery fabric conditional recipes should be under the conditional namespace! " + recipeId + "isn't!");
        return (T) RecipeManager.fromJson(recipeId, GsonHelper.getAsJsonObject(json, "recipe"));
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
    public static void registerBuiltInPack(String modId, ResourceLocation location, boolean alwaysEnabled){
        FabricLoader.getInstance().getModContainer(modId).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(location, modContainer, alwaysEnabled ? ResourcePackActivationType.ALWAYS_ENABLED : ResourcePackActivationType.NORMAL);
        });
    }

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static <T> List<Pair<List<String>, T>> findAPIs(Class<T> returnClazz, String name, Class<?> annotationClazz) {
        List<Pair<List<String>, T>> instances = Lists.newArrayList();

        FabricLoader.getInstance().getEntrypointContainers(name, returnClazz).forEach(entrypoint -> {
            String modId = entrypoint.getProvider().getMetadata().getId();
            T api = entrypoint.getEntrypoint();
            instances.add(new Pair<>(List.of(modId), api));
        });
        return instances;
    }
}

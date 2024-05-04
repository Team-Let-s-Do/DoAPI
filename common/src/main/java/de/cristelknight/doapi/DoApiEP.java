package de.cristelknight.doapi;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.nio.file.Path;
import java.util.List;

public class DoApiEP {
    @ExpectPlatform
    public static <T extends Recipe<?>> T fromJson(ResourceLocation recipeId, JsonObject json) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static void registerBuiltInPack(String modId, ResourceLocation location, boolean alwaysEnabled){
        throw new AssertionError();
    }
    @ExpectPlatform
    public static boolean isModLoaded(String modid){
        throw new AssertionError();
    }
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> List<Pair<List<String>, T>> findAPIs(Class<T> returnClazz, String name, Class<?> annotationClazz){
        throw new AssertionError();
    }
}

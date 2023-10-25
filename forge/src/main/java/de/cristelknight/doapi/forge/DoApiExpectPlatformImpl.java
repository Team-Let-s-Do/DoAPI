package de.cristelknight.doapi.forge;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.forge.common.packs.BuiltInPackRegistry;
import de.cristelknight.doapi.forge.common.registry.BurningBlockRegistry;
import de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatEntity;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformChestBoatEntity;
import de.cristelknight.doapi.forge.terraform.sign.SpriteIdentifierRegistry;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformHangingSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformWallHangingSignBlock;
import de.cristelknight.doapi.forge.terraform.sign.block.TerraformWallSignBlock;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoApiExpectPlatformImpl {


    /*
    public static <T> DataResult<Pair<Recipe<?>, T>> decode(DynamicOps<T> ops, JsonElement json) {
        JsonObject recipe = GsonHelper.getAsJsonObject(json.getAsJsonObject(), "recipe");
        JsonArray conditions = GsonHelper.getAsJsonArray(json.getAsJsonObject(), "conditions");

        int size = conditions.size();
        JsonObject forgeCondition;
        if(size == 1) forgeCondition = conditions.get(0).getAsJsonObject();
        else {
            forgeCondition = new JsonObject();
            forgeCondition.addProperty("type", "forge:and");
            forgeCondition.add("values", conditions);
        }

        JsonObject forgeRecipe = new JsonObject();
        forgeRecipe.addProperty("type", "forge:conditional");
        forgeRecipe.add("forge:condition", forgeCondition);

        JsonArray recipes = new JsonArray();

        JsonObject newRecipe = new JsonObject();
        newRecipe.add("forge:condition", forgeCondition);
        newRecipe.add("recipe", recipe);

        recipes.add(newRecipe);
        forgeRecipe.add("recipes", recipes);



        String string = new Gson().newBuilder().setPrettyPrinting().create().toJson(forgeRecipe);

        try {
            Files.write(Path.of("C:/Users/Admin/Desktop/recipe.json"), string.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataResult<Recipe<?>> parsed = ConditionalRecipe.SERIALZIER.codec().parse(JsonOps.INSTANCE, forgeRecipe);
        return SimpleConditionalRecipe.checkResult(parsed, ops);
    }
     */

    public static boolean isModLoaded(String modid) {
        ModList modList = ModList.get();
        if(modList != null){
            return modList.isLoaded(modid);
        }
        return isModPreLoaded(modid);
    }
    public static void registerBuiltInPack(String modId, ResourceLocation location, boolean alwaysEnabled){
        String stringPath = location.getPath();
        Path path = getResourceDirectory(modId, "resourcepacks/" + stringPath);
        if(path == null) return;
        String[] pathElements = stringPath.split("/");
        BuiltInPackRegistry.packResources.put(location, new Pair<>(new PathPackResources(pathElements[pathElements.length - 1], true, path), alwaysEnabled));
    }

    public static @Nullable Path getResourceDirectory(String modId, String subPath) {
        ModContainer container = ModList.get().getModContainerById(modId).orElse(null);
        if(container == null){
            DoApi.LOGGER.warn("Mod container for modId:" + modId + " is null");
            return null;
        }
        IModFile file = container.getModInfo().getOwningFile().getFile();
        Path path = file.findResource(subPath);
        if(path == null){
            DoApi.LOGGER.warn("Path for subPath: " + subPath + " in modId: " + modId + " is null");
        }
        return path;
    }



    public static boolean isModPreLoaded(String modid) {
        return getPreLoadedModInfo(modid) != null;
    }

    public static @Nullable ModInfo getPreLoadedModInfo(String modId){
        for(ModInfo info : LoadingModList.get().getMods()){
            if(info.getModId().equals(modId)) {
                return info;
            }
        }
        return null;
    }

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
    public static void registerBoatType(ResourceLocation resourceLocation, TerraformBoatType type) {
        TerraformBoatTypeRegistry.register(resourceLocation, type);
    }


    public static Boat createBoat(ResourceLocation boatTypeName, Level world, double x, double y, double z, boolean chest) {
        TerraformBoatType boatType = TerraformBoatTypeRegistry.get(boatTypeName);
        Boat boatEntity;
        if (chest) {
            TerraformChestBoatEntity chestBoat = new TerraformChestBoatEntity(world, x, y, z);
            chestBoat.setTerraformBoat(boatType);
            boatEntity = chestBoat;
        } else {
            TerraformBoatEntity boat = new TerraformBoatEntity(world, x, y, z);
            boat.setTerraformBoat(boatType);
            boatEntity = boat;
        }
        return boatEntity;
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        BurningBlockRegistry.add(burnOdd, igniteOdd, blocks);
    }


    public static void addSignSprite(ResourceLocation signTextureId) {
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(signTextureId);
    }

    public static <T> List<Pair<List<String>, T>> findAPIs(Class<T> returnClazz, String name, Class<?> annotationClazz) {
        return ApiFinder.scanForAPIs(annotationClazz, returnClazz);
    }

    public static Map<ResourceLocation, Boolean> getAllDoApiBoatTypeNamesAndRaft() {
        Map<ResourceLocation, Boolean> boats = new HashMap<>();
        for(Map.Entry<ResourceLocation, TerraformBoatType> entry : TerraformBoatTypeRegistry.entrySet()){
            boats.put(entry.getKey(), entry.getValue().isRaft());
        }
        return boats;
    }


    public static Block getSign(ResourceLocation signTextureId) {
        return new TerraformSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getWallSign(ResourceLocation signTextureId) {
        return new TerraformWallSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return new TerraformHangingSignBlock(hangingSignTextureId, hangingSignGuiTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN));
    }

    public static Block getWallHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return new TerraformWallHangingSignBlock(hangingSignTextureId, hangingSignGuiTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN));
    }



}

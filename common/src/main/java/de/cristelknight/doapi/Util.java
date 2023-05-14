package de.cristelknight.doapi;

import com.mojang.datafixers.util.Pair;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class Util {

    public static <T> List<T> getApis(Class<T> returnClazz, String name, Class<?> annotationClazz){
         List<T> apis = new ArrayList<>();
        for(Pair<List<String>, T> apiPair : DoApiExpectPlatform.findAPIs(returnClazz, name, annotationClazz)){
            apis.add(apiPair.getSecond());
        }
        return apis;
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(DeferredRegister<Block> registerB, Registrar<Block> registrarB, DeferredRegister<Item> registerI, Registrar<Item> registrarI, ResourceLocation name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        RegistrySupplier<T> toReturn = registerWithoutItem(registerB, registrarB, name, block);
        Item.Properties properties = new Item.Properties();
        if(tab != null) properties.tab(tab);
        registerItem(registerI, registrarI, name, () -> new BlockItem(toReturn.get(), properties));
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(DeferredRegister<Block> register, Registrar<Block> registrar, ResourceLocation path, Supplier<T> block) {
        if (Platform.isForge()) {
            return register.register(path.getPath(), block);
        }
        return registrar.register(path, block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(DeferredRegister<Item> register, Registrar<Item> registrar, ResourceLocation path, Supplier<T> itemSupplier) {
        if (Platform.isForge()) {
            return register.register(path.getPath(), itemSupplier);
        }
        return registrar.register(path, itemSupplier);
    }



    public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
        Objects.requireNonNull(pos, "BlockPos cannot be null");

        return tracking(world, new ChunkPos(pos));
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
        Objects.requireNonNull(world, "The world cannot be null");
        Objects.requireNonNull(pos, "The chunk pos cannot be null");

        return world.getChunkSource().chunkMap.getPlayers(pos, false);
    }

    public static Optional<Tuple<Float, Float>> getRelativeHitCoordinatesForBlockFace(BlockHitResult blockHitResult, Direction direction, Direction[] unAllowedDirections) {
        Direction direction2 = blockHitResult.getDirection();
        if(Arrays.stream(unAllowedDirections).toList().contains(direction2)) return Optional.empty();
        if (direction != direction2 && direction2 != Direction.UP  && direction2 != Direction.DOWN) {
            return Optional.empty();
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos().relative(direction2);
            Vec3 vec3 = blockHitResult.getLocation().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            float d = (float) vec3.x();
            float f = (float) vec3.z();

            float y = (float) vec3.y();

            if(direction2 == Direction.UP || direction2 == Direction.DOWN) direction2 = direction;
            return switch (direction2) {
                case NORTH -> Optional.of(new Tuple<>((float) (1.0 - d), y));
                case SOUTH -> Optional.of(new Tuple<>(d, y));
                case WEST -> Optional.of(new Tuple<>(f, y));
                case EAST -> Optional.of(new Tuple<>((float) (1.0 - f), y));
                case DOWN, UP -> Optional.empty();
            };
        }
    }
}

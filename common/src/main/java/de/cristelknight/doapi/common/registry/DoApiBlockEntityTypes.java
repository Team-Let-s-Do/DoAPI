package de.cristelknight.doapi.common.registry;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.common.block.entity.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class DoApiBlockEntityTypes {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(DoApi.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_ENTITY = create("storage", () -> BlockEntityType.Builder.of(
            StorageBlockEntity::new, getBlocksForEntity()).build(null));

    public static final RegistrySupplier<BlockEntityType<StandardBlockEntity>> STANDARD = create("standard", () -> BlockEntityType.Builder.of(
            StandardBlockEntity::new, DoApiBlocks.STANDARD.get(), DoApiBlocks.WALL_STANDARD.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = create("cabinet", () -> BlockEntityType.Builder.of(
            CabinetBlockEntity::new, getBlocksForEntity()).build(null));

    public static final RegistrySupplier<BlockEntityType<SideBoardBlockEntity>> SIDEBOARD_BLOCK_ENTITY = create("sideboard", () -> BlockEntityType.Builder.of(
            SideBoardBlockEntity::new, getBlocksForEntity()).build(null));

    private static Block[] getBlocksForEntity(){
        Set<Block> set = new HashSet<>();
        List<DoApiAPI> apis = Util.getApis(DoApiAPI.class, "doapi", DoApiPlugin.class);
        for(DoApiAPI api : apis){
            api.registerBlocks(set);
        }
        return set.toArray(new Block[0]);
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(path, type);
    }

    public static void init() {
        BLOCK_ENTITY_TYPES.register();
    }
}

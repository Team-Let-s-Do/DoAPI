package de.cristelknight.doapi.registry;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.block.entity.StorageBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class DoApiBlockEntityTypes {


    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(DoApi.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY).getRegistrar();


    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_ENTITY = create("storage", () -> BlockEntityType.Builder.of(
            StorageBlockEntity::new, getBlocksForStorage()).build(null));

    private static Block[] getBlocksForStorage(){
        Set<Block> set = new HashSet<>();
        List<DoApiAPI> apis = Util.getApis(DoApiAPI.class, "doapi", DoApiPlugin.class);
        for(DoApiAPI api : apis){
            api.registerBlocks(set);
        }
        return set.toArray(new Block[0]);
    }


    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new DoApiRL(path), type);
    }

    public static void loadClass() {
        
    }
}
